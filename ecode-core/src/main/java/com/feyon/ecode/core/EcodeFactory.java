package com.feyon.ecode.core;

/**
 * @author Feyon
 */
public interface EcodeFactory {

    /**
     * 从工厂中加载错误码消息
     * @param code 错误码
     * @return 错误码消息
     */
    String getMessage(String code);
}
