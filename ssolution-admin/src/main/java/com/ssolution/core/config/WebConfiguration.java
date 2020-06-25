package com.ssolution.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;

import com.ssolution.core.interceptor.MenuNavigationInterceptor;
import com.ssolution.core.views.ExcelXlsxView;

@Configuration
@EnableWebMvc
public class WebConfiguration implements WebMvcConfigurer {

    private static final int CACHE_PERIOD=600;

    @Autowired
    private ExcelXlsxView excelXlsxView;

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {

        MappingJackson2JsonView mappingJackson2JsonView = new MappingJackson2JsonView();
        registry.enableContentNegotiation(mappingJackson2JsonView);
        registry.enableContentNegotiation(excelXlsxView);
        registry.order(Ordered.HIGHEST_PRECEDENCE+2);
    }

    @Bean
    public ViewResolver configureViewResolver() {
        InternalResourceViewResolver viewResolve = new InternalResourceViewResolver();
        viewResolve.setPrefix("/WEB-INF/views/");
        viewResolve.setSuffix(".jsp");

        return viewResolve;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        PathResourceResolver pathResourceResolver = new PathResourceResolver();
        registry.addResourceHandler("/css/**").addResourceLocations("/css/").setCachePeriod(CACHE_PERIOD).resourceChain(true).addResolver(pathResourceResolver);
        registry.addResourceHandler("/font-awesome/**").addResourceLocations("/font-awesome/").setCachePeriod(CACHE_PERIOD).resourceChain(true).addResolver(pathResourceResolver);
        registry.addResourceHandler("/fonts/**").addResourceLocations("/fonts/").setCachePeriod(CACHE_PERIOD).resourceChain(true).addResolver(pathResourceResolver);
        registry.addResourceHandler("/img/**").addResourceLocations("/img/").setCachePeriod(CACHE_PERIOD).resourceChain(true).addResolver(pathResourceResolver);
        registry.addResourceHandler("/js/**").addResourceLocations("/js/").setCachePeriod(CACHE_PERIOD).resourceChain(true).addResolver(pathResourceResolver);
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(true);
        configurer.mediaType("json", MediaType.APPLICATION_JSON);
        configurer.mediaType("file", MediaType.APPLICATION_OCTET_STREAM);
        configurer.ignoreAcceptHeader(true);


    }


    @Bean
    public MenuNavigationInterceptor menuNavigationInterceptor() {
        return new MenuNavigationInterceptor();
    }


    @Bean
    public UrlBasedViewResolver viewResolver() {
        UrlBasedViewResolver tilesViewResolver = new UrlBasedViewResolver();
        tilesViewResolver.setViewClass(TilesView.class);
        tilesViewResolver.setOrder(Ordered.HIGHEST_PRECEDENCE+1);
        return tilesViewResolver;
    }

    @Bean
    public TilesConfigurer tilesConfigurer() {
        TilesConfigurer tiles = new TilesConfigurer();
        tiles.setDefinitions("/WEB-INF/views/tiles/tiles.xml");
        return tiles;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("redirect:system/login/login");
    }

    @Bean
    public CommonsRequestLoggingFilter requestLoggingFilter() {
        CommonsRequestLoggingFilter loggingFilter = new CommonsRequestLoggingFilter();
        loggingFilter.setIncludeClientInfo(true);
        loggingFilter.setIncludeQueryString(true);
        loggingFilter.setIncludePayload(true);
        return loggingFilter;
    }
}

