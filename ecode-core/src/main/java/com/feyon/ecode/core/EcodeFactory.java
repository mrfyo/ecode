package com.feyon.ecode.core;

/**
 * 该接口用于从错误码数据源（如JSON文件，数据库）中加载所有的定义的异常码并缓存，同时提供根据错误码(code)
 * 来查询已经缓存的错误码
 *
 * @author Feyon
 */
public interface EcodeFactory extends Reloadable{

    /**
     * 从工厂中加载错误码消息
     * @param code 错误码（notNull)
     * @return 错误码消息, 如果code时{@link null},将直接放回{@link null}
     */
    String getMessage(String code);


    /**
     * 从工厂中获取 Ecode
     * @param code 错误码（notNull)
     * @return 错误码消息, 如果code时{@link null},将直接放回{@link null}
     */
    Ecode getEcode(String code);


}
