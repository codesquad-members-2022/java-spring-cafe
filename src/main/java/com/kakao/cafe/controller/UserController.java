package com.kakao.cafe.controller;

import com.kakao.cafe.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/new")
    public String createForm() {
        return "user/form";
    }

    @PostMapping("/users")
    public String create(UserForm form) {
        userService.join(form);

        return "redirect:/users";
    }

    @GetMapping("/users")
    public String viewUserList(Model model) {
        model.addAttribute("users", userService.findAllUserForm());
        model.addAttribute("countOfTotalUser", userService.findAllUserForm().size());
        return "user/list";
    }

    @GetMapping("/users/{userId}")
    public String viewUserProfile(@PathVariable String userId, Model model) {
        model.addAttribute("user", userService.findOneByUserId(userId));
        return "user/profile";
    }
}
