package com.feyon.ecode.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Feyon
 */
public class AnnotationEcodeHandler implements EcodeHandler {

    private final Logger log = LoggerFactory.getLogger(AnnotationEcodeHandler.class);


    @Override
    public String extractCode(Class<?> ec) {
        if(ec.isAssignableFrom(Throwable.class)) {
            log.warn("the exceptionClass is not exception class, error use annotation [Ecode], class is, {}", ec);
            return null;
        }
        EcodeTag ecode = ec.getAnnotation(EcodeTag.class);
        if(ecode != null) {
            String code = ecode.code();
            if(code.isEmpty()) {
               log.warn("the Ecode.code cannot is empty, the Ecode could be solve.");
            }
            return code;
        }else {
            log.warn("the exceptionClass is not supported by Ecode, class is {}, ", ec);
        }
        return null;
    }

}
