package com.feyon.ecode.spring.autoconfigure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.feyon.ecode.core.*;
import com.feyon.ecode.spring.JsonEcodeFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Feyon
 */
@Configuration
@ConditionalOnProperty(name = "ecode.enabled", matchIfMissing = true)
public class EcodeAutoConfigure {

    public Class<? extends Ecode> getEcodeType() {
        return SimpleEcode.class;
    }

    @Bean
    @ConditionalOnMissingBean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    @ConditionalOnMissingBean
    public EcodeFactory ecodeFactory(ObjectMapper objectMapper) {
        Class<? extends Ecode> ecodeType = getEcodeType();
        return new JsonEcodeFactory(objectMapper, ecodeType);
    }

    @Bean
    @ConditionalOnMissingBean
    public EcodeHandler ecodeHandler() {
        return  new AnnotationEcodeHandler();
    }

    @Bean
    @ConditionalOnMissingBean
    public ExceptionFactory exceptionFactory() {
        SimpleExceptionFactory exceptionFactory = new SimpleExceptionFactory();
        exceptionFactory.setExceptionRootClass(EcodeException.class);
        return exceptionFactory;
    }

    @Bean
    @ConditionalOnMissingBean
    public EcodeManager ecodeManager(ExceptionFactory factory, EcodeFactory ecodeFactory, EcodeHandler ecodeHandler) {
        DefaultEcodeManager ecodeManager =  new DefaultEcodeManager(factory, ecodeFactory, ecodeHandler);
        EcodeUtils.setEcodeManager(ecodeManager);
        return ecodeManager;
    }


}
