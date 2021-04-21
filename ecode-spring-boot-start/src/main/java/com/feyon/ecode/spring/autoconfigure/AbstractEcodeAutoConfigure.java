package com.feyon.ecode.spring.autoconfigure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.feyon.ecode.core.*;
import com.feyon.ecode.core.AnnotationEcodeHandler;
import com.feyon.ecode.core.DefaultEcodeManager;
import com.feyon.ecode.core.SimpleExceptionFactory;
import com.feyon.ecode.spring.JsonEcodeFactory;

import javax.annotation.PostConstruct;

/**
 * @author Feyon
 */
public abstract class AbstractEcodeAutoConfigure {

    public EcodeFactory ecodeFactory(ObjectMapper objectMapper) {
        Class<? extends Ecode> ecodeType = getEcodeType();
        return new JsonEcodeFactory(objectMapper, ecodeType);
    }


    public EcodeHandler ecodeHandler() {
        return  new AnnotationEcodeHandler();
    }


    public ExceptionFactory exceptionFactory(EcodeFactory ecodeFactory, EcodeHandler ecodeHandler) {
        SimpleExceptionFactory exceptionFactory = new SimpleExceptionFactory(ecodeFactory, ecodeHandler);
        exceptionFactory.setExceptionRootClass(EcodeException.class);
        return exceptionFactory;
    }


    public EcodeManager ecodeManager(ExceptionFactory factory, EcodeFactory ecodeFactory, EcodeHandler ecodeHandler) {

        DefaultEcodeManager ecodeManager =  new DefaultEcodeManager(factory, ecodeFactory, ecodeHandler);
        if(factory == null) {
            ecodeManager.setExceptionFactory(new SimpleExceptionFactory(ecodeFactory, ecodeHandler));
        }
        EcodeUtils.setEcodeManager(ecodeManager);
        return ecodeManager;
    }


    /**
     * 指定 Ecode 的具体类型，用户序列化
     * @return Ecode 的具体类型
     */
    public abstract Class<? extends Ecode> getEcodeType();
}
