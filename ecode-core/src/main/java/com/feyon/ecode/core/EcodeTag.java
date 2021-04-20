package com.feyon.ecode.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Feyon
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EcodeTag {

    /**
     * 错误码（具有唯一性）
     * @return 错误码
     */
    String code();

    /**
     * 目前仅为提示作用
     * @return 标记信息
     */
    String message() default "";
}
