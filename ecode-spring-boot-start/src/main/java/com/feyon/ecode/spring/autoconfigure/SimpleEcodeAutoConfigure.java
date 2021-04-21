package com.feyon.ecode.spring.autoconfigure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.feyon.ecode.core.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author Feyon
 */
@Configuration
public class SimpleEcodeAutoConfigure extends AbstractEcodeAutoConfigure{

    @Override
    public Class<? extends Ecode> getEcodeType() {
        return SimpleEcode.class;
    }

    @Bean
    @ConditionalOnMissingBean(EcodeFactory.class)
    @ConditionalOnBean(ObjectMapper.class)
    @Override
    public EcodeFactory ecodeFactory(ObjectMapper objectMapper) {
        return super.ecodeFactory(objectMapper);
    }

    @Bean
    @ConditionalOnMissingBean(EcodeHandler.class)
    @Override
    public EcodeHandler ecodeHandler() {
        return super.ecodeHandler();
    }

    @Bean
    @ConditionalOnMissingBean(ExceptionFactory.class)
    @Override
    public ExceptionFactory exceptionFactory(EcodeFactory ecodeFactory, EcodeHandler ecodeHandler) {
        return super.exceptionFactory(ecodeFactory, ecodeHandler);
    }

    @Bean
    @ConditionalOnMissingBean(EcodeManager.class)
    @Override
    public EcodeManager ecodeManager(ExceptionFactory factory, EcodeFactory ecodeFactory, EcodeHandler ecodeHandler) {
        return super.ecodeManager(factory, ecodeFactory, ecodeHandler);
    }


}
