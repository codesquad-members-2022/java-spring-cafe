package com.kakao.cafe.controller;

import com.kakao.cafe.domain.user.UserForm;
import com.kakao.cafe.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private UserService userService;

    @GetMapping("/users/join")
    public String joinForm() {
        return "user/form";
    }

    @PostMapping("/users")
    public String create(UserForm userForm) {
        userService.createUser(userForm);
        return "redirect:/users";
    }
}
