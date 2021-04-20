package com.feyon.ecode.core;

import com.feyon.ecode.core.exception.SystemException;
import com.feyon.ecode.core.exception.UserException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ExceptionFactoryTest {

    private ExceptionFactory exceptionFactory;

    @Before
    public void setUp() throws Exception {
        EcodeFactory ecodeFactory = new AbstractEcodeFactory() {
            @Override
            public List<Ecode> getAllEcode() {
                List<Ecode> ecodes = new ArrayList<>(16);
                ecodes.add(new SimpleEcode("10000", "用户错误"));
                ecodes.add(new SimpleEcode("20000", "系统错误"));
                ecodes.add(new SimpleEcode("30000", "第三方错误"));
                return ecodes;
            }
        };
        // 必须手动重启 EcodeFactory
        ecodeFactory.reload();
        EcodeHandler ecodeHandler = new AnnotationEcodeHandler();
        exceptionFactory = new SimpleExceptionFactory(ecodeFactory, ecodeHandler);
    }

    @Test
    public void setExceptionRootClass() {

    }

    @Test
    public void newException() {
        RuntimeException exception = exceptionFactory.newException("30000");
        Assert.assertEquals(exception.getClass(), EcodeException.class);

    }

    @Test
    public void testNewException() {
        RuntimeException exception = exceptionFactory.newException(UserException.class, "10000");
        Assert.assertEquals(exception.getMessage(), "用户错误");
    }

    /**
     * 异常类必须实现 参数为 message 的构造方法
     */
    @Test
    public void testNewException2() {
        RuntimeException exception = exceptionFactory.newException(SystemException.class);
        Assert.assertEquals(exception.getMessage(), "系统错误");
    }
}