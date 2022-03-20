package com.kakao.cafe.controller;

import com.kakao.cafe.controller.dto.LoginDto;
import com.kakao.cafe.domain.User;
import com.kakao.cafe.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class LoginController {

    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/login")
    public String loginForm(Model model) {
        model.addAttribute("loginDto", new LoginDto());
        return "user/login";
    }

    @PostMapping("/users/login")
    public String login(@Valid LoginDto loginDto, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "user/login";
        }

        User user = userService.loginUser(loginDto.getUserId(), loginDto.getPassword());
        if (user == null) {
            bindingResult.reject("loginErrorMessage", "존재하지 않는 회원입니다.");
            return "user/login";
        }
        session.setAttribute("loginUser", user);
        return "redirect:/";
    }
}
