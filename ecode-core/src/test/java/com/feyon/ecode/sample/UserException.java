package com.feyon.ecode.sample;


/**
 * @author Feyon
 */
public class UserException extends RuntimeException {

    public UserException() {
    }

    public UserException(String message) {
        super(message);
    }
}