package com.kakao.cafe;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);

        registry.addViewController("/").setViewName("index");
        registry.addViewController("/sign-up").setViewName("user/form");
        registry.addViewController("/sign-in").setViewName("user/login");
        registry.addViewController("/post/form").setViewName("post/form");
    }
}
