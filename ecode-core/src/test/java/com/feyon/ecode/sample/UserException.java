package com.feyon.ecode.sample;

import com.feyon.ecode.core.Ecode;
import com.feyon.ecode.core.EcodeAware;

/**
 * @author Feyon
 */
@Ecode(code = "A0000")
public class UserException extends RuntimeException implements EcodeAware {

    private String message;

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        String sm = super.getMessage();
        return sm != null ? sm : this.message;
    }
}
