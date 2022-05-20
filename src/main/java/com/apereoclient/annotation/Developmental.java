package com.apereoclient.annotation;

import org.springframework.context.annotation.Profile;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Denounces a class from production. Any class marked with this annotation will be excluded when the
 * active profile is set as "prod"
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Profile({"!prod"})
public @interface Developmental {

}
