package com.gmail.chickenpowerrr.autoloader;

import com.google.common.reflect.ClassPath;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import java.util.stream.Collectors;

class Detector {

    private static final Logger LOGGER = Logger.getLogger(Detector.class.getSimpleName());

    private final Map<Class<?>, Object> loadedClasses = new ConcurrentHashMap<>();

    Detector(){}

    @SuppressWarnings("unchecked")
    <T> T getInstance(Class<T> clazz) {
        if(this.loadedClasses.containsKey(clazz)) {
            return (T) this.loadedClasses.get(clazz);
        } else {
            List<T> instances = getInstances(clazz, this.loadedClasses);
            if(!instances.isEmpty()) {
                return instances.get(0);
            } else {
                return null;
            }
        }
    }

    <T> List<T> getInstances(Class<T> clazz) {
        return getInstances(clazz, this.loadedClasses);
    }

    @SuppressWarnings("unchecked")
    private <T> List<T> getInstances(Class<T> clazz, Map<Class<?>, ?> loaded) {
        List<T> instances = new ArrayList<>();
        //We don't use a .collect(Collectors.toList()); because it will return a List<?> instead of a List<T>
        //and that isn't easily castable
        loaded.entrySet().stream().filter(entry -> clazz.isAssignableFrom(entry.getKey())).map(Map.Entry::getValue).forEach(object -> instances.add((T) object));
        return instances;
    }

    @SuppressWarnings("unchecked")
    <T> Collection<T> loadClasses(Class<?> mainClass, String packageName, Class<T> loadableTopLayer) {
        Collection<Class<?>> classes = getClasses(mainClass, packageName, loadableTopLayer);

        Map<Class<T>, T> loaded = new HashMap<>();
        T lastLoaded = null;

        for(Class<?> clazz : classes) {
            if(!clazz.isAnnotationPresent(Depend.class)) {
                loaded.put(cast(clazz), lastLoaded = (T) newInstance(clazz));
                this.loadedClasses.put(lastLoaded.getClass(), lastLoaded);
            }
        }

        classes.removeAll(loaded.keySet());

        while(lastLoaded != null) {
            lastLoaded = null;

            for(Class<?> clazz : classes) {
                if(allowedToLoad(clazz, cast(loaded))) {
                    loaded.put(cast(clazz), lastLoaded = (T) newInstance(clazz));
                    this.loadedClasses.put(lastLoaded.getClass(), lastLoaded);
                }
            }
            classes.removeAll(loaded.keySet());
        }

        if(!classes.isEmpty()) {
            LOGGER.warning("Couldn't load the following classes since they probably depend on each other: "
                    + classes.stream().map(Class::getName).collect(Collectors.joining(", ",  "[", "]")));
        }

        return loaded.values();
    }

    //A hacky way to be able to cast generics
    @SuppressWarnings("unchecked")
    private <T> T cast(Object object) {
        return (T) object;
    }

    private boolean allowedToLoad(Class<?> clazz, Map<Class<?>, ?> loaded) {
        return Arrays.stream(clazz.getAnnotation(Depend.class).depends()).noneMatch(loadableClass -> getInstances(loadableClass, loaded).isEmpty());
    }

    private Object newInstance(Class<?> clazz) {
        Optional<Constructor<?>> possibleConstructor = Arrays.stream(clazz.getConstructors())
                .filter(constructor -> Arrays.stream(constructor.getParameterTypes()).allMatch(param -> getInstance(param) != null))
                .max(Comparator.comparingInt(Constructor::getParameterCount));

        if(possibleConstructor.isPresent()) {
            Constructor<?> constructor = possibleConstructor.get();
            Object[] arguments = new Object[constructor.getParameterCount()];

            for(int i = 0; i < arguments.length; i++) {
                arguments[i] = getInstance(constructor.getParameterTypes()[i]);
            }

            try {
                return constructor.newInstance(arguments);
            } catch(Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new IllegalArgumentException(String.format("Failed loading class: '%s' since it doesn't have a constructor with values we own right now", clazz.getName()));
        }
    }

    private Collection<Class<?>> getClasses(Class<?> mainClass, String packageName, Class<?> top) {
        try {
            return ClassPath.from(mainClass.getClassLoader())
                    .getTopLevelClassesRecursive(packageName)
                    .stream()
                    .map(classInfo -> {
                        try {
                            return Class.forName(classInfo.getName());
                        } catch(ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .filter(aClass -> top.isAssignableFrom(aClass)
                            && !aClass.equals(top)
                            && !aClass.isEnum()
                            && !aClass.isInterface()
                            && !Modifier.isAbstract(aClass.getModifiers())
                            && !aClass.isAnnotationPresent(Skip.class))
                    .collect(Collectors.toSet());
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }
}
