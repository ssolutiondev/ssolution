package com.ssolution.core.config;


import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.util.Properties;


@Configuration
@EnableTransactionManagement
public class TransactionConfiguration {

    @Bean(name = "transactionInterceptor")
    public TransactionInterceptor transactionInterceptor(PlatformTransactionManager platformTransactionManager) {
        TransactionInterceptor transactionInterceptor = new TransactionInterceptor();
        transactionInterceptor.setTransactionManager(platformTransactionManager);
        Properties transactionAttributes = new Properties();
        transactionAttributes.setProperty("insert*", "PROPAGATION_REQUIRED,-Throwable,ISOLATION_READ_COMMITTED");
        transactionAttributes.setProperty("update*", "PROPAGATION_REQUIRED,-Throwable,ISOLATION_READ_COMMITTED");
        transactionAttributes.setProperty("delete*", "PROPAGATION_REQUIRED,-Throwable,ISOLATION_READ_COMMITTED");
        transactionAttributes.setProperty("*", "readOnly");
        transactionInterceptor.setTransactionAttributes(transactionAttributes);
        return transactionInterceptor;
    }

    @Bean
    public BeanNameAutoProxyCreator transactionAutoProxy() {
        BeanNameAutoProxyCreator transactionAutoProxy = new BeanNameAutoProxyCreator();
        transactionAutoProxy.setProxyTargetClass(true);
        transactionAutoProxy.setBeanNames("*Service");
        transactionAutoProxy.setInterceptorNames("transactionInterceptor");
        return transactionAutoProxy;
    }
}