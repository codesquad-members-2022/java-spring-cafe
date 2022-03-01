package com.kakao.cafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/user/form")
    public String signUp() {
        System.out.println("asf");
        return "user/form";
    }

}
