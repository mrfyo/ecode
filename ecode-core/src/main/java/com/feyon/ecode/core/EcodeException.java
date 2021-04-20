package com.feyon.ecode.core;

/**
 * @author Feyon
 */

public class EcodeException extends RuntimeException implements EcodeAware{

    public Ecode ecode;

    public EcodeException() { }

    public EcodeException(String message) {
        super(message);
    }

    public EcodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public EcodeException(Throwable cause) {
        super(cause);
    }

    @Override
    public void setEcode(Ecode ecode) {
        this.ecode = ecode;
    }

}
