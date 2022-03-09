package com.kakao.cafe.controller;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String list(Model model) {
        List<User> users = userService.findUsers();
        model.addAttribute("users",users);
        return "user/list";
    }

    @GetMapping("/users/form")
    public String form() {
        return "user/form";
    }

    @GetMapping("/users/create")
    public String create(User user) {
        userService.join(user);
        return "redirect:/users";
    }

    @GetMapping("/users/{userId}")
    public String profile(@PathVariable("userId") String userId, Model model) {
        model.addAttribute("user", userService.findOne(userId).get());
        return "user/profile";
    }
}
