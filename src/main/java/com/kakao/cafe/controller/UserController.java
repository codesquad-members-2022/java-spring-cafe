package com.kakao.cafe.controller;

import com.kakao.cafe.entity.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/signup")
    public String signup() {
        log.info("회원가입 페이지 접속");
        return "user/form";
    }

    @PostMapping("/create")
    public String users(@ModelAttribute User user) {
        log.info("signUp user = {}", user);
        return "redirect:/list";
    }
}
