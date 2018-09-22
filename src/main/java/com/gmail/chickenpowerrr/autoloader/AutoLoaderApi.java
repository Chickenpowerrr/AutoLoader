package com.gmail.chickenpowerrr.autoloader;

import java.util.Collection;
import java.util.List;

/**
 * This api makes sure you'll get access to all of
 * the methods you needed to automatically load classes
 * and access already instantiated objects.
 *
 * @author Chickenpowerrr
 * @since  1.0.0
 */
public interface AutoLoaderApi {

    /**
     * Gives access to the API methods
     *
     * @return a valid instance of the AutoLoader API
     * to make sure you'll be able to do the things you
     * need to do with the API
     */
    static AutoLoaderApi getApi() {
        return AutoLoaderApiImpl.getInstance();
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
     *                         LinkedList and an ArrayList, this argument will be List.class.
     * @param <T>              the generic that will define the generic of the returned collection.
     *                         It is defined by the value given for the loadableTopLayer
     * @return a Collection that contains all of the instances instantiated by this method
     */
    <T> Collection<T> loadClasses(Class<?> mainClass, String packageName, Class<T> loadableTopLayer);

    /**
     * Loads all classes that match the given criteria
     *
     * @param mainClass        the class whose classloader will
     *                         have to match the classloaders of the
     *                         classes that will be loaded this defines also the package all of
     *                         the classes should be inside of
     * @param loadableTopLayer this highest abstraction layer that all classes
     *                         you want to load do match. For example, if you have a
     *                         LinkedList and an ArrayList, this argument will be List.class
     * @param <T>              the generic that will define the generic of the returned collection.
     *                         It is defined by the value given for the loadableTopLayer
     * @return a Collection that contains all of the instances instantiated by this method
     */
    <T> Collection<T> loadClasses(Class<?> mainClass, Class<T> loadableTopLayer);

    /**
     * Gives you an instance of an instantiated class
     *
     * @param clazz the class whose instance we want
     * @param <T>   the generic that defines the returntype
     * @return the instance the given class if it's already loaded by the autoloader
     */
    <T> T getInstance(Class<T> clazz);

    /**
     * Gives you instances of instantiated classes
     *
     * @param topLayer his highest abstraction layer that all classes
     *                 you want to load do match. For example, if you have a
     *                 LinkedList and an ArrayList, this argument will be List.class
     * @param <T>      the generic that will define the generic of the returned collection.
     *                 It is defined by the value given for the topLayer
     * @return a List that contains all of the instances that match the given criteria and
     *         are already loaded on the moment this method has been invoked
     */
    <T> List<T> getInstances(Class<T> topLayer);
}
