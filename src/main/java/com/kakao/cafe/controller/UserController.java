package com.kakao.cafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("users/signup")
    public String signup(){
        return "user/form";
    }
}
