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
        setExceptionRootClass(EcodeException.class);
    }

    public void setEcodeFactory(EcodeFactory ecodeFactory) {
        this.ecodeFactory = ecodeFactory;
    }

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

    protected Class<? extends RuntimeException> getExceptionRootClass() {
        if(this.rootExceptionClass == null) {
            throw new RuntimeException("the rootExceptionClass cannot is null");
        }
        return this.rootExceptionClass;
    }

    @Override
    public RuntimeException newException(Class<? extends RuntimeException> exType) {
        String code = this.ecodeHandler.extractCode(exType);
        return newException(exType, code);
    }

    @Override
    public RuntimeException newException(Class<? extends RuntimeException> exType, String code) {
        Ecode ecode = getEcodeFromFactory(code);
        RuntimeException exception;
        if(ecode == null) {
            exception = createException(exType, "");
        }else {
            exception =  createException(exType, ecode.getMessage());
            setEcodeToException(exception, ecode);
        }
        return exception;
    }

    @Override
    public RuntimeException newException(String code) {
        Ecode ecode = getEcodeFromFactory(code);
        RuntimeException exception;
        if(ecode == null) {
            exception = createException(getExceptionRootClass(), "");
        }else {
            exception =  createException(getExceptionRootClass(), ecode.getMessage());
            setEcodeToException(exception, ecode);
        }
        return exception;
    }

    private void setEcodeToException(RuntimeException exception, Ecode ecode) {
        if(exception instanceof EcodeSupport) {
            ((EcodeSupport)exception).setEcode(ecode);
        }else {
            log.warn("the rootExceptionClass is not implements EcodeAware, " +
                    "exception created will only normal exception.");
        }
    }

    /**
     * 从{@link EcodeFactory}获取{@link Ecode}
     * @param code 错误码
     * @return 如果工厂中存在，返回{@link Ecode},否则返回 null.
     */
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
            String errMsg = "the rootExceptionClass is not Exception or the Exception not constructor that's param is 'message'";
            log.error(errMsg);
            throw new EcodeException(errMsg);
        }
    }

}
