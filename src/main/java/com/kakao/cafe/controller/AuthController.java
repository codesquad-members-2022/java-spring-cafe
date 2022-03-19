package com.kakao.cafe.controller;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.domain.UserLoginRequest;
import com.kakao.cafe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String formLogin() {
        return "user/login";
    }

    @PostMapping("/login")
    public String login(UserLoginRequest loginRequest, HttpSession httpSession) {
        User user = userService.login(loginRequest);
        if (user == null) {
            return "user/login_failed";
        }
        httpSession.setAttribute("sessionedUserId", user);
        return "redirect:/";
    }
}
