package com.gmail.chickenpowerrr.autoloader;

import com.google.common.reflect.ClassPath;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class is the most import part of the project. It is the class that
 * actually does all of the stuff that is needed to make this project to what
 * it is. It loads classes, caches them and defines if the criteria do match
 * certain objects
 *
 * @author Chickenpowerrr
 * @since  1.0.0
 */
class Detector {

    private final Loader loader;

    Detector(Loader loader) {
        this.loader = loader;
    }

    /**
     * Looks if a class does depend on non loaded classes
     *
     * @param clazz  the class whose depend annotation (if present) should be check
     * @param loaded the map that contains all of the loaded classes
     * @return true if it doesn't depend on non-loaded classes, false if at least one class should
     *         be loaded before this class will be allowed to load
     */
    boolean allowedToLoad(Class<?> clazz, Map<Class<?>, ?> loaded) {
        return Arrays.stream(clazz.getAnnotation(Depend.class).depends()).noneMatch(loadableClass -> this.loader.getInstances(loadableClass, loaded).isEmpty());
    }

    /**
     * Checks all of the classes that are inside of the given package,
     * do share the classloader used by the mainClass and use the given
     * topLayer. It will also check if the class isn't abstract, an interface
     * an enum, the top layer itself or contains the Skip annotation. Those
     * classes will be ignored
     *
     * @param mainClass    the class whose classloader will
     *                     have to match the classloaders of the
     *                     classes that will be loaded
     * @param packageName  the highest package all of the classes that will
     *                     get loaded will need to be inside
     * @param top          this highest abstraction layer that all classes
     *                     you want to load do match. For example, if you have a
     *                     LinkedList and an ArrayList, this argument will be List.class.
     * @return a Collection that contains all of the classes found by this method
     */
    Collection<Class<?>> getClasses(Class<?> mainClass, String packageName, Class<?> top) {
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
