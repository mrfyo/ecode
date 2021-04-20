package com.feyon.ecode.core;

/**
 * 如果异常类实现了该接口，将可以动态实现注入并加载 {@link Ecode} 信息，实现该接口的类一定是
 * {@link Exception}的子类，否则再程序运行过程中，可能会忽略该接口的功能。
 * @author Feyon
 */
public interface EcodeSupport {

    /**
     * 注入 Ecode
     * @param ecode {@link Ecode}
     */
    void setEcode(Ecode ecode);

    /**
     * 获取 Ecode
     * @return {@link Ecode}
     */
    Ecode getEcode();
}
