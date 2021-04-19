package com.feyon.ecode.core;

import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author Feyon
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Component
@Documented
@Scope("prototype")
public @interface Ecode {

    @AliasFor(annotation = Component.class)
    String value() default "";

    String code();

    String message() default "";

    boolean used() default false;

}
