package com.kakao.cafe.controller;

import com.kakao.cafe.service.AuthService;
import com.kakao.cafe.service.UserService;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/user/login")
    public String login(String userId, String password, HttpSession session) {
        authService.validateUser(userId, password);
        session.setAttribute("sessionUser", userService.findOne(userId));

        return "redirect:/";
    }

    @GetMapping("/user/logout")
    public String logout(HttpSession session) {
        session.invalidate();

        return "redirect:/";
    }
}
