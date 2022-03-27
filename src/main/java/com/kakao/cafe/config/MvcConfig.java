package com.kakao.cafe.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    private final HandlerInterceptor authInterceptor;

    public MvcConfig(HandlerInterceptor handlerInterceptor) {
        this.authInterceptor = handlerInterceptor;
    }

    // https://imbf.github.io/spring/2020/05/03/Spring-HiddenHttpMethodFilter.html
    @Bean
    public HiddenHttpMethodFilter httpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }

    // https://stackoverflow.com/questions/24661289/spring-boot-not-serving-static-content
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
            .addResourceLocations("classpath:/static/")
            .setCachePeriod(20);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(authInterceptor)
            .addPathPatterns("/articles/**")
            .excludePathPatterns();
    }

}
