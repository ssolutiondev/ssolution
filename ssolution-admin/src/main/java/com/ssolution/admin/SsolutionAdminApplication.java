package com.ssolution.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
@ComponentScan(basePackages = {"com.ssolution.admin", "com.ssolution.core"})
public class SsolutionAdminApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SsolutionAdminApplication.class);
    }

	public static void main(String[] args) {
		SpringApplication.run(SsolutionAdminApplication.class, args);

	}

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

}