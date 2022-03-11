package com.kakao.cafe.controller;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.dto.LoginForm;
import com.kakao.cafe.service.UserService;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView login(LoginForm loginForm, HttpSession session) {
        User user = userService.login(loginForm);
        session.setAttribute("loginUser", user);

        ModelAndView modelAndView = new ModelAndView("redirect:/users");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("loginUser");
        return "redirect:/users";
    }

}
