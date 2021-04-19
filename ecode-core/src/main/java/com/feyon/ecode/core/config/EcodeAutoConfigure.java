package com.feyon.ecode.core.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.feyon.ecode.core.*;
import com.feyon.ecode.core.gmt.AnnotationEcodeHandler;
import com.feyon.ecode.core.gmt.DefaultEcodeManager;
import com.feyon.ecode.core.gmt.JsonEcodeFactory;
import com.feyon.ecode.core.gmt.SimpleExceptionFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author Feyon
 */
@Configuration
public class EcodeAutoConfigure {

    @Bean
    @ConditionalOnMissingBean(ObjectMapper.class)
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    @ConditionalOnMissingBean(EcodeFactory.class)
    public EcodeFactory ecodeFactory(ObjectMapper objectMapper) {
        return new JsonEcodeFactory(objectMapper);
    }

    @Bean
    @ConditionalOnMissingBean(EcodeHandler.class)
    public EcodeHandler ecodeHandler() {
        return  new AnnotationEcodeHandler();
    }

    @Bean
    @ConditionalOnMissingBean(ExceptionFactory.class)
    public ExceptionFactory exceptionFactory(EcodeFactory ecodeFactory, EcodeHandler ecodeHandler) {
        SimpleExceptionFactory exceptionFactory = new SimpleExceptionFactory(ecodeFactory, ecodeHandler);
        exceptionFactory.setExceptionRootClass(EcodeException.class);

        return exceptionFactory;
    }

    @Bean
    @ConditionalOnMissingBean(EcodeManager.class)
    public EcodeManager ecodeManager(ExceptionFactory exceptionFactory) {
        return new DefaultEcodeManager(exceptionFactory);
    }

    @PostConstruct
    public void ecodeUtil(EcodeManager ecodeManager) {
        EcodeUtils.setExceptionFactory(ecodeManager);
    }

}
