package com.kakao.cafe.user.controller;

import com.kakao.cafe.user.model.User;
import com.kakao.cafe.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String userList(Model model) {
        model.addAttribute("users", userService.findUsers());
        return "user/list";
    }

    @GetMapping("/users/join")
    public String joinPage() {
        return "user/form";
    }

    @PostMapping("/users/create")
    public String join(User user) {
        log.info("User: {}", user);
        userService.join(user);
        return "redirect:/users";
    }
}
