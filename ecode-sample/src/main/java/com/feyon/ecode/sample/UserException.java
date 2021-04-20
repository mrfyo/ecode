package com.feyon.ecode.sample;

import com.feyon.ecode.core.EcodeTag;

/**
 * @author Feyon
 */
@EcodeTag(code = "10000")
public class UserException extends RuntimeException{
    public UserException() {
    }

    public UserException(String message) {
        super(message);
    }

    public UserException(String message, Throwable cause) {
        super(message, cause);
    }
}
