package com.feyon.ecode.core;

/**
 * @author Feyon
 */

public class EcodeException extends RuntimeException {

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
}
