package com.feyon.ecode.core;

/**
 * @author Feyon
 */
public interface ExceptionFactory {


    /**
     * 获取 Ecode 工厂类
     * @return {@link EcodeFactory}
     */
    EcodeFactory getEcodeFactory();

    /**
     * 获取 Ecode 处理类
     * @return {@link EcodeHandler}
     */
    EcodeHandler getEcodeHandler();

    /**
     * 设置 根异常类类型
     * @param rootClass 根异常类类型
     */
    void setExceptionRootClass(Class<? extends RuntimeException> rootClass);

    /**
     * 生成异常类
     * @param exType 自定义异常类
     * @return 代理异常类
     */
    RuntimeException newInstance(Class<? extends RuntimeException> exType);

    /**
     * 生成异常类，直接指定 code
     * @param code 错误码的 code
     * @return 代理异常类
     */
    RuntimeException newInstance(String code);


}
