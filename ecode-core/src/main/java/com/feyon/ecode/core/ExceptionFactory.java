package com.feyon.ecode.core;

/**
 * 异常工厂类，主要用途是生成指定类型或者默认类型的异常类。
 * 1. 如果指定类型实现了接口{@link EcodeSupport}，该工厂类会再创建异常类后，会自动注入{@link Ecode}
 * 2. 如果指定异常类型标注了{@link EcodeTag}, 会根据 EcodeTag 提供给信息创建异常类。
 *
 * 通过该工厂类动态创建异常类和直接 new 一个异常类的区别是，该工厂类会更具提供的错误码，去查询错误码数据源中查找对应的错误码信息，
 * 从而实现动态，已修改的错误码管理。
 * @author Feyon
 *
 */
public interface ExceptionFactory {

    /**
     * 设置 根异常类类型（实现了 {@link EcodeSupport}）, 默认是使用 {@link EcodeException}
     * @param exType   根异常类类型， 同时实现 {@link EcodeSupport}
     */
    void setExceptionRootClass(Class<? extends RuntimeException>  exType);

    /**
     * 根据给定的异常类型，生成新的异常类，并动态注入相应错误的异常信息
     * @param exType 自定义异常类，并且标注了 {@link EcodeTag}
     * @return 代理异常类并且实现了 {@link EcodeSupport}
     */
    RuntimeException newException(Class<? extends RuntimeException> exType);

    /**
     * 根据给定的异常类型生成指定的异常类，同时根据指定的异常码动态注入相应错误的异常信息
     * @param exType 自定义异常类，实现接口 {@link EcodeSupport}
     * @param code 有效错误码
     * @return 指定类型的异常类
     */
    RuntimeException newException(Class<? extends RuntimeException> exType, String code);

    /**
     * 根据给定的错误码，生成默认根异常类
     * @param code 错误码
     * @return 根异常类 实现接口 {@link EcodeSupport}，默认是 {@link EcodeException}
     */
    RuntimeException newException(String code);


}
