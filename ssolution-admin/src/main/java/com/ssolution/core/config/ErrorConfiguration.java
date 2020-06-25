package com.ssolution.core.config;


import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
public class ErrorConfiguration implements ErrorPageRegistrar {
    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {
        HttpStatus[] httpStatuses = HttpStatus.values();
        for(HttpStatus httpStatus : httpStatuses){
            if(httpStatus.isError() == false) continue;
            registry.addErrorPages(new ErrorPage(httpStatus, "/error/" + httpStatus.value()));

        }

    }
}


