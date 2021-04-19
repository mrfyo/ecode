package com.feyon.ecode.core;

/**
 * @author Feyon
 */
public interface Ecode {

    /**
     * 获取错误码消息
     * @return 错误码消息
     */
    String getMessage();

    /**
     * 获取错误码值
     * @return 错误码值
     */
    String getCode();

}
