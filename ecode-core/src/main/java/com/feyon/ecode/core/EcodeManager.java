package com.feyon.ecode.core;

/**
 * @author Feyon
 */
public interface EcodeManager {

    /**
     * return the {@link ExceptionFactory}
     * @return the ExceptionFactory object.
     */
    ExceptionFactory getExceptionFactory();

    /**
     * return the {@link EcodeFactory}
     * @return the EcodeFactory object.
     */
    EcodeFactory getEcodeFactory();

    /**
     * return the {@link EcodeHandler}
     * @return the EcodeHandle object.
     */
    EcodeHandler getEcodeHandler();



}
