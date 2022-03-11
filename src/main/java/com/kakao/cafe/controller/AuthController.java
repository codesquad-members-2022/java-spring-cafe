package com.kakao.cafe.controller;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.dto.LoginDto;
import com.kakao.cafe.service.UserService;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private static final String SESSION_USER = "SESSION_USER";

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login/form")
    public String formLogin() {
        return "user/login";
    }

    @PostMapping("/login")
    public String login(LoginDto loginDto, HttpSession session) {
        User user = userService.login(loginDto);
        session.setAttribute(SESSION_USER, user);
        return "redirect:/users";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(SESSION_USER);
        return "redirect:/users";
    }

}
