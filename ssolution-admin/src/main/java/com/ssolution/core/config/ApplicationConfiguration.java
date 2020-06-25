package com.ssolution.core.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "app.config")
@NoArgsConstructor
@Getter
@Setter
@ToString
@MapperScan("com.ssolution.**.mapper")
public class ApplicationConfiguration {

    private int loginFailCnt;

    private boolean isAvailDupLogin;
}
