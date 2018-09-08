package com.gmail.chickenpowerrr.autoloader;

import lombok.AccessLevel;
import lombok.Getter;

import java.util.Collection;
import java.util.List;

final class AutoLoaderApiImpl implements AutoLoaderApi {

    @Getter(AccessLevel.PACKAGE) private static final AutoLoaderApiImpl instance = new AutoLoaderApiImpl();

    private final Detector detector = new Detector();

    private AutoLoaderApiImpl(){}

    @Override
    public <T> Collection<T> loadClasses(Class<?> mainClass, String packageName, Class<T> loadableTopLayer) {
        return this.detector.loadClasses(mainClass, packageName, loadableTopLayer);
    }

    @Override
    public <T> Collection<T> loadClasses(Class<?> mainClass, Class<T> loadableTopLayer) {
        return loadClasses(mainClass, mainClass.getPackage().getName(), loadableTopLayer);
    }

    @Override
    public <T> T getInstance(Class<T> clazz) {
        return this.detector.getInstance(clazz);
    }

    @Override
    public <T> List<T> getInstances(Class<T> clazz) {
        return this.detector.getInstances(clazz);
    }
}
