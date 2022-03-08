package com.kakao.cafe;

import com.kakao.cafe.repository.user.UserMemoryRepository;
import com.kakao.cafe.repository.user.UserRepository;
import com.kakao.cafe.service.user.UserService;
import com.kakao.cafe.service.user.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public UserService userService() {
        return new UserServiceImpl(userRepository());
    }

    @Bean
    public UserRepository userRepository() {
        return new UserMemoryRepository();
    }
}
