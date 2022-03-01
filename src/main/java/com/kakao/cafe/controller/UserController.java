package com.kakao.cafe.controller;

import com.kakao.cafe.domain.UserInformation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @GetMapping("users/form")
    public String getUserForm() {
        return "user/form";
    }

    @GetMapping("users")
    public String getUserList() {
        return "user/list";
    }

    @GetMapping("users/{userId}")
    public String getUserProfile(@PathVariable String userId) {
        return "user/profile";
    }

    @PostMapping("users")
    public String createUserInformation(UserInformation userInformation) {
        return "redirect:users";
    }
}
