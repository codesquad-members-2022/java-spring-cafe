package com.kakao.cafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.kakao.cafe.domain.User;

@Controller
public class UserController {

    @GetMapping("/user/form")
    public String createForm(){
        return "user/form";
    }

    @PostMapping("/user/form")
    public String create(UserForm form){
        User user = new User();
        user.setEmail(form.getEmail());
        user.setUserId(form.getUserId());
        user.setPassword(form.getPassword());
    }

    @GetMapping("/create")
    public String create() {
        return "create";
    }
}
