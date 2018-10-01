package com.gmail.chickenpowerrr.autoloader;

import lombok.Getter;

import java.lang.reflect.Constructor;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import java.util.stream.Collectors;

class Loader {

    private static final Logger LOGGER = Logger.getLogger(Loader.class.getSimpleName());

    @Getter private final Detector detector = new Detector(this);
    private final Map<Class<?>, Object> loadedClasses = new ConcurrentHashMap<>();

    Loader() {

    }

    /**
     * Gives you an instance of an instantiated class
     *
     * @param clazz the class whose instance we want
     * @param <T>   the generic that defines the returntype
     * @return the instance the given class if it's already loaded by the autoloader
     */
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

    /**
     * Gives you instances of instantiated classes
     *
     * @param topLayer his highest abstraction layer that all classes
     *                 you want to load do match. For example, if you have a
     *                 LinkedList and an ArrayList, this argument will be List.class
     * @param <T>      the generic that will define the generic of the returned collection.
     *                 It is defined by the value given for the topLayer
     * @return a Collection that contains all of the instances that match the given criteria and
     *         are already loaded on the moment this method has been invoked
     */
    <T> List<T> getInstances(Class<T> topLayer) {
        return getInstances(topLayer, this.loadedClasses);
    }

    /**
     * Gets all instances out of the given map that do extend or implement
     * the given class
     *
     * @param clazz  this highest abstraction layer that all classes
     *               you want to load do match. For example, if you have a
     *               LinkedList and an ArrayList, this argument will be List.class
     * @param loaded the map that contains all of the required instances
     * @param <T>    the generic that will define the generic for the returntype
     * @return the requested instances that are filtered out of the map
     */
    @SuppressWarnings("unchecked")
    <T> List<T> getInstances(Class<T> clazz, Map<Class<?>, ?> loaded) {
        return cast(loaded.entrySet().stream().filter(entry -> clazz.isAssignableFrom(entry.getKey())).map(Map.Entry::getValue).collect(Collectors.toList()));
    }

    /**
     * Loads all classes that match the given criteria
     *
     * @param mainClass        the class whose classloader will
     *                         have to match the classloaders of the
     *                         classes that will be loaded
     * @param packageName      the highest package all of the classes that will
     *                         get loaded will need to be inside
     * @param loadableTopLayer this highest abstraction layer that all classes
     *                         you want to load do match. For example, if you have a
     *                         LinkedList and an ArrayList, this argument will be List.class
     * @param <T>              the generic that will define the generic of the returned collection.
     *                         It is defined by the value given for the loadableTopLayer
     * @return a Collection that contains all of the instances instantiated by this method
     */
    @SuppressWarnings("unchecked")
    <T> Collection<T> loadClasses(Class<?> mainClass, String packageName, Class<T> loadableTopLayer) {
        Collection<Class<?>> classes = this.detector.getClasses(mainClass, packageName, loadableTopLayer);

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
                if(this.detector.allowedToLoad(clazz, cast(loaded))) {
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

    /**
     * Creates a new instance of a class based on the best
     * constructor
     *
     * @param clazz the class whose instance we want
     * @return      a new instance
     */
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
}
