package com.loto.lte.core.config;

import com.loto.lte.core.common.CustomThreadPoolTaskExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import javax.validation.Validator;

@Configuration
public class WebServiceConfig {
    @Bean
    public ApplicationEventMulticaster applicationEventMulticaster() {
        SimpleApplicationEventMulticaster applicationEventMulticaster = new SimpleApplicationEventMulticaster();
        CustomThreadPoolTaskExecutor taskExecutor = new CustomThreadPoolTaskExecutor();
        taskExecutor.initialize();
        applicationEventMulticaster.setTaskExecutor(taskExecutor);
        return applicationEventMulticaster;
    }

    @Bean
    public Validator validator() {
        return new LocalValidatorFactoryBean();
    }

//    @Bean
//    public WebServiceMessageFactory webServiceMessageFactory() {
//        Map<String, Object> props = new HashMap<>();
//        props.put(SOAPMessage.WRITE_XML_DECLARATION, "true");
//        props.put(SOAPMessage.CHARACTER_SET_ENCODING, "utf-8");
//        SaajSoapMessageFactory messageFactory = new SaajSoapMessageFactory();
//        messageFactory.setMessageProperties(props);
//        messageFactory.afterPropertiesSet();
//        return messageFactory;
//    }
}
