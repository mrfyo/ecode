package com.feyon.ecode.core;

import java.io.Serializable;

/**
 * @author Feyon
 */
public class SimpleEcode implements Ecode, Serializable {


    private String code;

    private String message;

    public SimpleEcode() {
    }

    public SimpleEcode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "SimpleErrorCode{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
