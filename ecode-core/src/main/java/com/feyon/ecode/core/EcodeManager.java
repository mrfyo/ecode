package com.feyon.ecode.core;

/**
 * @author Feyon
 */
public interface EcodeManager extends EcodeFactory, ExceptionFactory {

    ExceptionFactory getExceptionFactory();

}
