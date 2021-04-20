package com.feyon.ecode.core;

/**
 * @author Feyon
 */
public interface EcodeHandler {

    /**
     * 从异常类中获取错误码
     * @param exceptionClass 异常类
     * @return 错误码
     */
    String extractCode(Class<?> exceptionClass);


}
