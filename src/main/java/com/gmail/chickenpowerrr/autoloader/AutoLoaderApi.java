package com.gmail.chickenpowerrr.autoloader;

import java.util.Collection;
import java.util.List;

public interface AutoLoaderApi {

    static AutoLoaderApi getApi() {
        return AutoLoaderApiImpl.getInstance();
    }

    <T> Collection<T> loadClasses(Class<?> mainClass, String packageName, Class<T> loadableTopLayer);

    <T> Collection<T> loadClasses(Class<?> mainClass, Class<T> loadableTopLayer);

    <T> T getInstance(Class<T> clazz);

    <T> List<T> getInstances(Class<T> clazz);
}
