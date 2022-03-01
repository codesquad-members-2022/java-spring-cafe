package com.kakao.cafe.user.controller;

import com.kakao.cafe.user.model.User;
import com.kakao.cafe.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/join")
    public String showJoinPage() {
        return "user/form";
    }

    @PostMapping("/users/create")
    public String join(User user) {
        log.info("User: {}", user);
        userService.join(user);
        return "redirect:/users";
    }
}
