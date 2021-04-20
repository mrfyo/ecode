package com.feyon.ecode.core;

import com.feyon.ecode.core.exception.UserException;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class AnnotationEcodeHandlerTest {


    @Test
    public void extractCode() {
        EcodeHandler ecodeHandler = new AnnotationEcodeHandler();
        String code = ecodeHandler.extractCode(UserException.class);
        Assert.assertEquals(code, "10001");
    }
}