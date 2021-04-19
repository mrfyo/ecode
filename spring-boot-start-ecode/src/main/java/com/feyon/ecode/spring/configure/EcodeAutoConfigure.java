package com.feyon.ecode.spring.configure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.feyon.ecode.core.*;
import com.feyon.ecode.core.AnnotationEcodeHandler;
import com.feyon.ecode.core.DefaultEcodeManager;
import com.feyon.ecode.core.SimpleExceptionFactory;
import com.feyon.ecode.spring.JsonEcodeFactory;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author Feyon
 */
@Configuration
public abstract class EcodeAutoConfigure {

    public EcodeFactory ecodeFactory(ObjectMapper objectMapper) {
        return new JsonEcodeFactory(objectMapper, SimpleEcode.class);
    }


    public EcodeHandler ecodeHandler() {
        return  new AnnotationEcodeHandler();
    }


    public ExceptionFactory exceptionFactory(EcodeFactory ecodeFactory, EcodeHandler ecodeHandler) {
        SimpleExceptionFactory exceptionFactory = new SimpleExceptionFactory(ecodeFactory, ecodeHandler);
        exceptionFactory.setExceptionRootClass(EcodeException.class);
        return exceptionFactory;
    }


    public EcodeManager ecodeManager(EcodeFactory ecodeFactory, EcodeHandler ecodeHandler) {
        return new DefaultEcodeManager(ecodeFactory, ecodeHandler);
    }

    @PostConstruct
    public void ecodeUtil(EcodeManager ecodeManager) {
        EcodeUtils.setExceptionFactory(ecodeManager);
    }

}
