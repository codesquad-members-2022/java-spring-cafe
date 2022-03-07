package com.kakao.cafe.config;

import com.kakao.cafe.repository.ArticleRepository;
import com.kakao.cafe.repository.MemoryArticleRepository;
import com.kakao.cafe.repository.MemoryUserRepository;
import com.kakao.cafe.repository.UserRepository;
import com.kakao.cafe.service.ArticleService;
import com.kakao.cafe.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    @Bean
    public UserService userService() {
        return new UserService(userRepository());
    }

    @Bean
    public UserRepository userRepository() {
        return new MemoryUserRepository();
    }

    @Bean
    public ArticleService articleService() {
        return new ArticleService(articleRepository());
    }

    @Bean
    public ArticleRepository articleRepository() {
        return new MemoryArticleRepository();
    }
}
