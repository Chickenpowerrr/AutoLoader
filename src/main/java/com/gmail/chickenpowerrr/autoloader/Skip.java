package com.gmail.chickenpowerrr.autoloader;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation can be used to prevent a class from
 * auto loading even though it meets all of the other
 * requirements that are needed for a class to load.
 *
 * @author Chickenpowerrr
 * @since  1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Skip {

    /**
     * The reason a class should be skipping while automatically
     * loading all the classes. This is optional but it can be
     * handy to tell the other developers on your project why this
     * specific class should not be loaded.
     *
     * @return the reason why a class should not be automatically
     * loaded
     */
    String reason() default "No reason found";
}
