package com.kakao.cafe.controller;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserController {
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/new")
    public String createForm() {
        return "user/create";
    }

    @GetMapping("/user/login")
    public String loginForm() {
        return "user/login";
    }

    @GetMapping("/user/list")
    public String listForm(Model model) {
        List<User> userList = userService.findUsers();
        model.addAttribute("userList", userList);
        return "user/list";
    }

    @GetMapping("/user/profile")
    public String profileForm() {
        return "user/profile";
    }

    @PostMapping("/user")
    public String create(User user) {
        userService.join(user);
        return "redirect:/user/list";
    }
}
