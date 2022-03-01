package com.kakao.cafe.controller;

import com.kakao.cafe.domain.UserInformation;
import com.kakao.cafe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String getUserList(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "/user/list";
    }

    @GetMapping("/users/{userId}")
    public String getUserProfile(@PathVariable String userId, Model model) {
        model.addAttribute("user", userService.findOneUser(userId).get());
        return "/user/profile";
    }

    @PostMapping("/users")
    public String createUserInformation(UserInformation userInformation) {

        userService.join(userInformation);

        return "redirect:/users";
    }
}
