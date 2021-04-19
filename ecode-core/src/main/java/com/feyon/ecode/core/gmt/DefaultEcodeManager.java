package com.feyon.ecode.core.gmt;

import com.feyon.ecode.core.*;

/**
 * @author Feyon
 */
public class DefaultEcodeManager<T> implements EcodeManager {

    private ExceptionFactory exceptionFactory;

    public DefaultEcodeManager(ExceptionFactory exceptionFactory) {
        this.exceptionFactory = exceptionFactory;
    }

    public void setExceptionFactory(ExceptionFactory exceptionFactory) {
        this.exceptionFactory = exceptionFactory;
    }

    @Override
    public String getMessage(String code) {
        return getEcodeFactory().getMessage(code);
    }

    @Override
    public Ecode getEcode(String code) {
        return getEcodeFactory().getEcode(code);
    }


    @Override
    public EcodeFactory getEcodeFactory() {
        return this.exceptionFactory.getEcodeFactory();
    }

    @Override
    public EcodeHandler getEcodeHandler() {
        return this.exceptionFactory.getEcodeHandler();
    }

    @Override
    public ExceptionFactory getExceptionFactory() {
        return this.exceptionFactory;
    }

    @Override
    public void setExceptionRootClass(Class<? extends RuntimeException> rootClass) {
        this.exceptionFactory.setExceptionRootClass(rootClass);
    }

    @Override
    public RuntimeException newInstance(Class<? extends RuntimeException> exType) {
        return this.exceptionFactory.newInstance(exType);
    }

    @Override
    public RuntimeException newInstance(String code) {
        return this.exceptionFactory.newInstance(code);
    }


}
