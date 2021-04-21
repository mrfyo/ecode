package com.feyon.ecode.sample.springboot;

import com.feyon.ecode.core.Ecode;
import com.feyon.ecode.core.EcodeSupport;

/**
 * @author Feyon
 */
public class TestException extends RuntimeException implements EcodeSupport {

    protected Ecode ecode;

    public TestException() {
    }

    public TestException(String message) {
        super(message);
    }

    public TestException(String message, Throwable cause) {
        super(message, cause);
    }

    public TestException(Throwable cause) {
        super(cause);
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
