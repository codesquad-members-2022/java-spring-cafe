package com.kakao.cafe.config;

import com.kakao.cafe.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .order(1)
                .addPathPatterns("/users/{id}", "/users/{id}/update")
                .addPathPatterns("/qna/write-qna", "/qna/show/{id}")
                .addPathPatterns("qna/update/{id}", "qna/delete/{id}")
                .addPathPatterns("/qna/{articleId}/reply/write", "/qna/{articleId}/reply/{id}/delete");


    }
}
