package com.kakao.cafe.config;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.service.UserService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Random;

@Component
public class InitData {

    private final UserService userService;

    public InitData(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    public void init() {
        String[] emails = {"naver", "kakao", "gmail"};
        Random random = new Random();

        for (int i = 1; i <= 10; i++) {
            User user = new User();
            user.setUserId("userId" + i);
            user.setPassword("password" + i);
            user.setName("name" + (random.nextInt(100) + 1));
            user.setEmail("email" + (random.nextInt(100) + 1) + "@" + emails[random.nextInt(3)] + ".com");
            userService.join(user);
        }
    }
}
