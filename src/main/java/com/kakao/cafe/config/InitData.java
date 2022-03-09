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
            String userId = "userId" + i;
            String name = "name" + (random.nextInt(100) + 1);
            String password = "password" + i;
            String email = "email" + (random.nextInt(100) + 1) + "@" + emails[random.nextInt(3)] + ".com";
            User user = new User(userId, password, name, email);
            try {
                userService.join(user);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
