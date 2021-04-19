package com.feyon.ecode.core.gmt;

import com.feyon.ecode.core.EcodeAware;
import com.feyon.ecode.core.EcodeFactory;
import com.feyon.ecode.core.EcodeHandler;
import com.feyon.ecode.core.EcodeLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.Assert;

/**
 * @author Feyon
 */
public class EcodeAwareProcessor implements EcodeLoader, BeanPostProcessor, BeanFactoryAware {

    private final Logger log = LoggerFactory.getLogger(EcodeAwareProcessor.class);

    private EcodeHandler ecodeHandler;

    private EcodeFactory ecodeFactory;

    private BeanFactory beanFactory;

    public void setEcodeFactory(EcodeFactory ecodeFactory) {
        this.ecodeFactory = ecodeFactory;
    }

    public EcodeFactory getEcodeFactory() {
        return ecodeFactory;
    }

    public void setEcodeHandler(EcodeHandler ecodeHandler) {
        this.ecodeHandler = ecodeHandler;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }


    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(bean instanceof EcodeAware) {
            EcodeFactory ef = this.ecodeFactory;
            if (ef == null) {
                Assert.state(this.beanFactory != null,
                        "BeanFactory required if no EcodeFactory explicitly specified");
                ef = beanFactory.getBean(EcodeFactory.class);
            }
            EcodeHandler eh = this.ecodeHandler;
            if (eh == null) {
                Assert.state(this.beanFactory != null,
                        "BeanFactory required if no EcodeHandle explicitly specified");
                eh = beanFactory.getBean(EcodeHandler.class);
            }
            String code = eh.getCode(bean.getClass());

            if (code == null || code.isEmpty()) {
                log.warn("the exception class is provide the code, {}", bean.getClass());
            } else {
                String message = ef.getMessage(code);
                if (message == null || message.isEmpty()) {
                    log.warn("the code is not define or the message of ErrorCode is empty, code is {}", code);
                } else {
                    ((EcodeAware) bean).setMessage(message);
                }
            }
        }
        return bean;
    }


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
