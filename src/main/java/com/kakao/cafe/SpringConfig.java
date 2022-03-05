package com.kakao.cafe;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kakao.cafe.repository.MemoryUserRepository;
import com.kakao.cafe.repository.UserRepository;
import com.kakao.cafe.service.UserService;

@Configuration
public class SpringConfig {
//Controller는 여기에 등록하면 안되고 따로 @Controller, @Autowired 를 붙여줘야한다.
    @Bean
    public UserService userService() {
        return new UserService(userRepository());
    }
    @Bean
    public UserRepository userRepository() {
        return new MemoryUserRepository();
    }

}
