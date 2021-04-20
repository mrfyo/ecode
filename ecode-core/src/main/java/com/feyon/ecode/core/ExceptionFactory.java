package com.feyon.ecode.core;

/**
 * @author Feyon
 */
public interface ExceptionFactory {


    /**
     * 获取 Ecode 工厂类
     * @param ecodeFactory  {@link EcodeFactory}
     */
    void setEcodeFactory(EcodeFactory ecodeFactory);

    /**
     * 获取 Ecode 处理类
     * @param ecodeHandler {@link EcodeHandler}
     */
    void setEcodeHandler(EcodeHandler ecodeHandler);



    /**
     * 设置 根异常类类型
     * @return  根异常类类型
     */
    Class<? extends RuntimeException>  getExceptionRootClass();

    /**
     * 生成异常类
     * @param exType 自定义异常类
     * @return 代理异常类
     */
    RuntimeException newException(Class<? extends RuntimeException> exType);

    /**
     * 生成异常类，直接指定 code
     * @param code 错误码的 code
     * @return 代理异常类
     */
    RuntimeException newException(String code);


}
