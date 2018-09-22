package com.gmail.chickenpowerrr.autoloader;

import lombok.AccessLevel;
import lombok.Getter;

import java.util.Collection;
import java.util.List;

/**
 * This class is the working part behind the AutoLoaderApi.
 * This class forces the Detector to do the things the Api demands
 * us to do
 *
 * @author Chickenpowerrr
 * @since  1.0.0
 */
final class AutoLoaderApiImpl implements AutoLoaderApi {

    @Getter(AccessLevel.PACKAGE) private static final AutoLoaderApiImpl instance = new AutoLoaderApiImpl();
    private final Detector detector = new Detector();

    /**
     * Makes sure nobody else will be able to create a new instance
     *
     */
    private AutoLoaderApiImpl(){}

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
     *
     */
    @Override
    public <T> Collection<T> loadClasses(Class<?> mainClass, String packageName, Class<T> loadableTopLayer) {
        return this.detector.loadClasses(mainClass, packageName, loadableTopLayer);
    }

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
     *
     */
    @Override
    public <T> Collection<T> loadClasses(Class<?> mainClass, Class<T> loadableTopLayer) {
        return loadClasses(mainClass, mainClass.getPackage().getName(), loadableTopLayer);
    }

    /**
     * Gives you an instance of an instantiated class
     *
     * @param clazz the class whose instance we want
     * @param <T>   the generic that defines the returntype
     * @return the instance the given class if it's already loaded by the autoloader
     */
    @Override
    public <T> T getInstance(Class<T> clazz) {
        return this.detector.getInstance(clazz);
    }

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
    @Override
    public <T> List<T> getInstances(Class<T> topLayer) {
        return this.detector.getInstances(topLayer);
    }
}
