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

		registry.addViewController("/user/form").setViewName("user/form");
		registry.addViewController("/user/profile").setViewName("user/profile");

		registry.addViewController("/user/login_failed").setViewName("user/login_failed");

		registry.addViewController("/qna/form").setViewName("qna/form");
		registry.addViewController("/qna/show").setViewName("qna/show");
	}

}
