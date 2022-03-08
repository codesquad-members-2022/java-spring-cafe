package com.kakao.cafe;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kakao.cafe.repository.ArticleRepository;
import com.kakao.cafe.repository.MemoryArticleRepository;
import com.kakao.cafe.repository.MemoryUserRepository;
import com.kakao.cafe.repository.UserRepository;

@Configuration
public class SpringConfig {
    //Controller는 여기에 등록하면 안되고 따로 @Controller를 붙여줘야한다.

    @Bean
    public UserRepository userRepository() {
        return new MemoryUserRepository();
    }

    @Bean
    public ArticleRepository articleRepository() {
        return new MemoryArticleRepository();
    }

}
