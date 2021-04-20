package com.feyon.ecode.sample;

import com.feyon.ecode.core.EcodeTag;

/**
 * @author Feyon
 */
@EcodeTag(code = "20000")
public class SystemException extends RuntimeException {

    public SystemException() {
    }

    public SystemException(String message) {
        super(message);
    }

    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }

    public SystemException(Throwable cause) {
        super(cause);
    }
}
