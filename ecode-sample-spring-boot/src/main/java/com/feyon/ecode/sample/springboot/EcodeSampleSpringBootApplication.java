package com.feyon.ecode.sample.springboot;

import com.feyon.ecode.core.ExceptionFactory;
import com.feyon.ecode.core.SimpleExceptionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author Feyon
 */
@SpringBootApplication
public class EcodeSampleSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcodeSampleSpringBootApplication.class, args);
    }


    @Bean
    public ExceptionFactory exceptionFactory() {
        SimpleExceptionFactory simpleExceptionFactory = new SimpleExceptionFactory();
        simpleExceptionFactory.setExceptionRootClass(TestException.class);
        return simpleExceptionFactory;
    }
}
