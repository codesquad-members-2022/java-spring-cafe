package kr.codesquad.cafe.system;

import kr.codesquad.cafe.system.intercepter.LoginRequiredInterceptor;
import kr.codesquad.cafe.system.intercepter.UserAuthenticationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);

        registry.addViewController("/join").setViewName("users/form");
        registry.addViewController("/questions/new").setViewName("qna/form");
        registry.addViewController("/badRequest").setViewName("badRequest");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginRequiredInterceptor())
                .addPathPatterns("/questions/**")
                .addPathPatterns("/users/**");

        registry.addInterceptor(userAuthenticationInterceptor())
                .addPathPatterns("/users/*/form");
    }

    @Bean
    public LoginRequiredInterceptor loginRequiredInterceptor() {
        return new LoginRequiredInterceptor();
    }

    @Bean
    public UserAuthenticationInterceptor userAuthenticationInterceptor() {
        return new UserAuthenticationInterceptor();
    }
}
