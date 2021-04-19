package com.feyon.ecode.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Feyon
 */
public class SimpleExceptionFactory implements ExceptionFactory {

    private final Logger log = LoggerFactory.getLogger(SimpleExceptionFactory.class);

    private Class<? extends RuntimeException> rootExceptionClass;

    private EcodeFactory ecodeFactory;

    private EcodeHandler ecodeHandler;

    public SimpleExceptionFactory(EcodeFactory ecodeFactory, EcodeHandler ecodeHandler) {
        this.ecodeFactory = ecodeFactory;
        this.ecodeHandler = ecodeHandler;
    }

    @Override
    public void setEcodeFactory(EcodeFactory ecodeFactory) {
        this.ecodeFactory = ecodeFactory;
    }

    @Override
    public void setEcodeHandler(EcodeHandler ecodeHandler) {
        this.ecodeHandler = ecodeHandler;
    }

    public EcodeFactory getEcodeFactory() {
        return this.ecodeFactory;
    }

    public EcodeHandler getEcodeHandler() {
        return this.ecodeHandler;
    }

    @Override
    public void setExceptionRootClass(Class<? extends RuntimeException> rootClass) {
        this.rootExceptionClass = rootClass;
    }

    @Override
    public RuntimeException newException(Class<? extends RuntimeException> exType) {
        String code = this.ecodeHandler.extractCode(exType);
        Ecode ecode = getEcodeFromFactory(code);
        if(ecode != null) {
            return createException(exType, ecode.getMessage());
        }
        return new EcodeException("the exception instance create fail");
    }

    @Override
    public RuntimeException newException(String code) {
        Ecode ecode = getEcodeFromFactory(code);
        if(ecode != null) {
            return createException(rootExceptionClass, ecode.getMessage());
        }
        return new EcodeException("the exception instance create fail");
    }

    private Ecode getEcodeFromFactory(String code) {
        if(ecodeFactory == null) {
            String message = "the exception factory need the ecode-factory, but ecodeFactory is null";
            log.error(message);
            throw new RuntimeException(message);
        }
        if (code == null || code.isEmpty()) {
            log.warn("the code cannot be empty, code is {}", code);
        }
        return ecodeFactory.getEcode(code);
    }

    private RuntimeException createException(Class<? extends RuntimeException> clazz, String message) {
        try {
            Constructor<? extends RuntimeException> constructor = clazz.getConstructor(String.class);
            return  constructor.newInstance(message);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            String errMsg = "the rootExceptionClass is not Exception";
            log.error(errMsg);
            throw new EcodeException(errMsg);
        }
    }

}
