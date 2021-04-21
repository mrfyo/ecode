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



    private EcodeManager ecodeManager;


    public SimpleExceptionFactory() {
        setExceptionRootClass(EcodeException.class);
    }




    public EcodeFactory getEcodeFactory() {
        return this.ecodeManager.getEcodeFactory();
    }

    public EcodeHandler getEcodeHandler() {
        return this.ecodeManager.getEcodeHandler();
    }

    @Override
    public void setExceptionRootClass(Class<? extends RuntimeException> rootClass) {
        this.rootExceptionClass = rootClass;
    }

    @Override
    public void setEcodeManager(EcodeManager manager) {
        this.ecodeManager = manager;
    }

    protected Class<? extends RuntimeException> getExceptionRootClass() {
        if(this.rootExceptionClass == null) {
            throw new RuntimeException("the rootExceptionClass cannot is null");
        }
        return this.rootExceptionClass;
    }

    @Override
    public RuntimeException newException(Class<? extends RuntimeException> exType) {
        String code = getEcodeHandler().extractCode(exType);
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
        if(code == null) {
            return new IllegalArgumentException("code cannot be null");
        }
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
            log.warn("the rootExceptionClass is not implements EcodeSupport, " +
                    "exception created will only normal exception.");
        }
    }

    /**
     * 从{@link EcodeFactory}获取{@link Ecode}
     * @param code 错误码
     * @return 如果工厂中存在，返回{@link Ecode},否则返回 null.
     */
    private Ecode getEcodeFromFactory(String code) {
        if(getEcodeFactory() == null) {
            String message = "the exception factory need the ecode-factory, but ecodeFactory is null";
            log.error(message);
            throw new RuntimeException(message);
        }
        if (code == null || code.isEmpty()) {
            log.warn("the code cannot be empty");
        }
        return getEcodeFactory().getEcode(code);
    }

    private RuntimeException createException(Class<? extends RuntimeException> clazz, String message) {
        RuntimeException exception = null;
        try {
            Constructor<? extends RuntimeException> constructor = clazz.getConstructor(String.class);
            exception = constructor.newInstance(message);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            String errMsg = "the Exception not constructor that's param is 'message'";
            log.warn(errMsg);
            try {
                exception = clazz.newInstance();
            } catch (InstantiationException | IllegalAccessException ie) {
                errMsg = "the Exception cannot construct new Instance. " + clazz;
                log.warn(errMsg);
                throw new EcodeException(errMsg);
            }
        }
        return exception;
    }

}
