package com.ssolution.core.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.session.SessionManagementFilter;

import com.ssolution.core.filter.ExcludeSessionManagementFilter;
import com.ssolution.core.listeners.SessionEventListener;


@Configuration
@EnableWebSecurity
@EnableGlobalAuthentication
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 허용할 정적 자원 경로를 지정 한다.
        web.ignoring().antMatchers("/css/**",
                "/font-awesome/**",
                "/fonts/**",
                "/img/**",
                "/js/**",
                "/favicon.ico");

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String SESSION_TIME_OUT_URL = "/error/401";


        http
                .addFilterBefore(excludeSessionManagementFilter(), SessionManagementFilter.class)
                .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and()
                .sessionManagement()
                .maximumSessions(1)
                .and()
                .invalidSessionUrl(SESSION_TIME_OUT_URL)
                .sessionAuthenticationErrorUrl(SESSION_TIME_OUT_URL)
                .enableSessionUrlRewriting(true)
                .sessionCreationPolicy(SessionCreationPolicy.NEVER)
                .and()
                .authorizeRequests()
                .antMatchers("/system/login/login", "/system/login/loginAction", "/").permitAll()
                .anyRequest()
                //.hasRole("USER")
                .anonymous()
        ;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Session Evnet Listener를 등록 한다.
     *
     * @return SessionEventListener
     */
    @Bean
    public SessionEventListener sessionEventListener() {
        return new SessionEventListener();
    }

    @Bean
    public ExcludeSessionManagementFilter excludeSessionManagementFilter() {
        return new ExcludeSessionManagementFilter();
    }
}

