package com.kakao.cafe.controller;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/create")
    public String createForm() {
        return "/user/form";
    }

    @PostMapping("/user/create")
    public String create(UserForm form) {
        User user = new User(form);
        userService.join(user);
        System.out.println("posted!");
        return "redirect:/";
    }

    @GetMapping("/users")
    public String viewList() {
        return "user/list";
    }


}
