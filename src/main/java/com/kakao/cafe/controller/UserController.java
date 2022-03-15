package com.kakao.cafe.controller;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@RequestMapping("/user")
@Controller
public class UserController {

    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/create")
    public String createForm() {
        return "user/create";
    }

    @PostMapping("/create")
    public String createUser(User user) {
        userService.createUser(user);

        return "redirect:/user/list";
    }

    @GetMapping("/list")
    public String userList(Model model) {
        List<User> users = userService.findAllUsers();
        model.addAttribute("users", users);

        return "user/list";
    }

    @GetMapping("/{userName}")
    public String userProfile(@PathVariable String userName, Model model) {
        User foundUser = userService.findUser(userName);
        model.addAttribute("foundUser", foundUser);

        return "user/profile";
    }           
}
