package com.feyon.ecode.core;

import com.feyon.ecode.core.exception.UserException;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class AnnotationEcodeHandlerTest {

    @EcodeTag(code = "10001")
    static class AException extends  RuntimeException {

    }

    @Test
    public void extractCode() {
        EcodeHandler ecodeHandler = new AnnotationEcodeHandler();
        String code = ecodeHandler.extractCode(AException.class);
        Assert.assertEquals(code, "10001");
    }
}