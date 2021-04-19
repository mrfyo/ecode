package com.feyon.ecode.core.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.feyon.ecode.core.EcodeFactory;
import com.feyon.ecode.core.EcodeHandler;
import com.feyon.ecode.core.EcodeLoader;
import com.feyon.ecode.core.gmt.AnnotationEcodeHandler;
import com.feyon.ecode.core.gmt.JsonEcodeFactory;
import com.feyon.ecode.core.gmt.EcodeAwareProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
    @ConditionalOnMissingBean(EcodeLoader.class)
    public BeanPostProcessor ecodeLoader(EcodeFactory ecodeFactory, EcodeHandler ecodeHandler) {
        EcodeAwareProcessor processor = new EcodeAwareProcessor();
        processor.setEcodeFactory(ecodeFactory);
        processor.setEcodeHandler(ecodeHandler);
        return processor;
    }



}
