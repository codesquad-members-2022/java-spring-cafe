package com.kakao.cafe.config;

import com.kakao.cafe.repository.MemoryUserRepository;
import com.kakao.cafe.repository.UserRepository;
import com.kakao.cafe.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    public UserService userService(){
        return new UserService(memoryUserRepository());
    }

    @Bean
    public UserRepository memoryUserRepository(){
        return new MemoryUserRepository();
    }
}
