package com.kakao.cafe;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kakao.cafe.repository.MemoryUserRepository;
import com.kakao.cafe.repository.UserRepository;
import com.kakao.cafe.service.UserService;

@Configuration
public class SpringConfig {
    @Bean
    public UserRepository userRepository() {
        return new MemoryUserRepository();
    }

    @Bean
    public UserService userService() {
        return new UserService(userRepository());
    }

}
