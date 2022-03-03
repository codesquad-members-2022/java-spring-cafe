package com.kakao.cafe.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
//
//        registry.addViewController("/users/form").setViewName("user/form");
//        registry.addViewController("/users/login").setViewName("user/login");
//        registry.addViewController("/questions/form").setViewName("qna/form");
//    }
    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("static/**").addResourceLocations("classpath:/static/").setCachePeriod(31556926);
    }
}
