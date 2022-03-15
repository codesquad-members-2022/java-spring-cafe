package com.kakao.cafe.controller;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/join")
    public String createForm() {
        return "user/form";
    }

    @PostMapping("/user/join")
    public String create(@ModelAttribute User user) {
        userService.join(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String memberList(Model model) {
        List<User> users = userService.findMembers();
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/users/{userIndex}")
    public String showProfile(@PathVariable int userIndex, Model model) {
        User user = userService.findOne(userIndex);
        model.addAttribute("users", user);
        return "user/profile";
    }

}
