package com.ssolution.core.config;


import com.ssolution.admin.system.util.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;

@Configuration
public class MessageConfiguration {

    @Autowired
    private MessageSource messageSource;

    @Bean
    public MessageUtil message(){
        MessageUtil messageUtil = new MessageUtil();
        messageUtil.setMessageSourceAccessor(new MessageSourceAccessor(messageSource));

        return messageUtil;
    }
}
