package com.feyon.ecode.core;

/**
 * @author Feyon
 */
public class DefaultEcodeManager implements EcodeManager {

    private ExceptionFactory exceptionFactory;

    private EcodeFactory ecodeFactory;

    private EcodeHandler ecodeHandler;

    public DefaultEcodeManager(EcodeFactory ecodeFactory, EcodeHandler ecodeHandler) {
        this(new SimpleExceptionFactory(ecodeFactory, ecodeHandler), ecodeFactory, ecodeHandler);
    }


    public DefaultEcodeManager(ExceptionFactory exceptionFactory, EcodeFactory ecodeFactory, EcodeHandler ecodeHandler) {
        this.exceptionFactory = exceptionFactory;
        this.ecodeFactory = ecodeFactory;
        this.ecodeHandler = ecodeHandler;
    }

    public void setExceptionFactory(ExceptionFactory exceptionFactory) {
        this.exceptionFactory = exceptionFactory;
    }

    public void setEcodeHandler(EcodeHandler ecodeHandler) {
        this.ecodeHandler = ecodeHandler;
    }

    public void setEcodeFactory(EcodeFactory ecodeFactory) {
        this.ecodeFactory = ecodeFactory;
    }

    @Override
    public EcodeFactory getEcodeFactory() {
        return this.ecodeFactory;
    }

    @Override
    public EcodeHandler getEcodeHandler() {
        return this.ecodeHandler;
    }

    @Override
    public ExceptionFactory getExceptionFactory() {
        return this.exceptionFactory;
    }


}
