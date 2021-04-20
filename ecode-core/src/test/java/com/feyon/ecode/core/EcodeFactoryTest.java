package com.feyon.ecode.core;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class EcodeFactoryTest {

    EcodeFactory factory;

    @Before
    public void init() {
        factory = new AbstractEcodeFactory() {
            @Override
            public List<Ecode> getAllEcode() {
                List<Ecode> ecodes = new ArrayList<>(16);
                ecodes.add(new SimpleEcode("1", "错误 1"));
                ecodes.add(new SimpleEcode("2", "错误 2"));
                ecodes.add(new SimpleEcode("3", "错误 3"));
                return ecodes;
            }
        };
        // 仅支持手动启动
        factory.reload();
    }



    @Test
    public void getMessage() {
        String message = factory.getMessage("1");
        Assert.assertEquals(message, "错误 1");
    }

    @Test
    public void getEcode() {
        Ecode ecode = factory.getEcode("2");
        Assert.assertEquals(ecode.getCode(), "2");
        Assert.assertEquals(ecode.getMessage(), "错误 2");
    }
}