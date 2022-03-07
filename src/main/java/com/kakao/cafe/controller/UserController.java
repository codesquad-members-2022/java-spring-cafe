package com.kakao.cafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("user/new")
    public String createForm() {
        return "user/create";
    }

    @GetMapping("user/login")
    public String loginForm() {
        return "user/login";
    }

    @GetMapping("user/list")
    public String listForm() {
        return "user/list";
    }
}
