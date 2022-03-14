package com.kakao.cafe.controller;

import com.kakao.cafe.dto.UserLoginRequest;
import com.kakao.cafe.dto.UserResponse;
import com.kakao.cafe.service.UserService;
import com.kakao.cafe.util.SessionUtil;
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
        UserResponse user = userService.login(request);
        session.setAttribute(SessionUtil.SESSION_USER, user);
        return "redirect:/users";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(SessionUtil.SESSION_USER);
        return "redirect:/users";
    }

}
