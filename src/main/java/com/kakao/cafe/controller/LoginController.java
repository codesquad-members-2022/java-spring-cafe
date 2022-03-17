package com.kakao.cafe.controller;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.dto.LoginParam;
import com.kakao.cafe.exception.user.UserDomainException;
import com.kakao.cafe.service.LoginService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    public String login(LoginParam loginParam, HttpSession session) {
        User user = loginService.checkInfo(loginParam);
        if (!user.confirmPassword(loginParam.getPassword())) {
            return "user/login_failed";
        }

        session.setAttribute("userInfo", user);
        return "redirect:/users";
    }

    @ExceptionHandler({UserDomainException.class})
    private ResponseEntity<String> except(UserDomainException ex) {
        return new ResponseEntity<>(ex.getMessage(), ex.getHttpStatus());
    }
}
