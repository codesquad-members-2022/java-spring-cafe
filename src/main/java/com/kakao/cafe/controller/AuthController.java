package com.kakao.cafe.controller;

import com.kakao.cafe.dto.UserLoginRequest;
import com.kakao.cafe.service.UserService;
import com.kakao.cafe.session.SessionUser;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login/form")
    public String formLogin() {
        return "user/login";
    }

    @PostMapping("/login")
    public String login(UserLoginRequest request, HttpSession session) {
        SessionUser user = userService.login(request);
        session.setAttribute(SessionUser.SESSION_KEY, user);
        return "redirect:/users";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(SessionUser.SESSION_KEY);
        return "redirect:/users";
    }

}
