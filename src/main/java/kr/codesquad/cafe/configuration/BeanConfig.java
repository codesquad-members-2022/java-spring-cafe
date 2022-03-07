package kr.codesquad.cafe.configuration;

import kr.codesquad.cafe.users.OnMemoryUserRepository;
import kr.codesquad.cafe.users.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public UserRepository userRepository() {
        return new OnMemoryUserRepository();
    }
}
