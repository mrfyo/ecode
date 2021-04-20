package com.feyon.ecode.core;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author Feyon
 */
public class EcodeAgent implements InvocationHandler, EcodeSupport {
    private final Ecode code;

    private final RuntimeException exception;

    public EcodeAgent(Ecode code, RuntimeException exception) {
        this.code = code;
        this.exception = exception;
    }

    @Override
    public Ecode getEcode() {
        return code;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(exception, args);
    }
}
