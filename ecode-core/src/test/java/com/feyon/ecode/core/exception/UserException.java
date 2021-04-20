package com.feyon.ecode.core.exception;

import com.feyon.ecode.core.Ecode;
import com.feyon.ecode.core.EcodeSupport;

/**
 * @author Feyon
 */
public class UserException extends RuntimeException implements EcodeSupport {

    protected Ecode ecode;

    public UserException() {
    }

    public UserException(String message) {
        super(message);
    }

    @Override
    public void setEcode(Ecode ecode) {
        this.ecode = ecode;
    }

    @Override
    public Ecode getEcode() {
        return this.ecode;
    }
}
