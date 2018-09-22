package com.gmail.chickenpowerrr.autoloader;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation gives classes the ability
 * to depend on other classes. This means that if
 * class A depends on class B, class B will always be
 * loaded before class A gets loaded.
 *
 * @author Chickenpowerrr
 * @since  1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Depend {

    /**
     * Defines all of the classes that should be loaded before
     * this class will be loaded.
     *
     * @return the classes that should be loaded before the current
     * class will get loaded
     */
    Class<?>[] depends();

}
