package com.kakao.cafe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.service.UserService;

@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/list")
    public String viewUserList(Model model) {
        List<User> users = userService.findUsers();
        model.addAttribute("users", users);
        return "/user/list";
    }

    @GetMapping("/user/form")
    public String viewUserForm() {
        return "/user/form";
    }

    @PostMapping("/user")
    public String signIn(User user) {
        userService.join(user);
        return "redirect:/user/list";
    }

    //TODO: 컨트롤러와 DTO가 domain(ex: User)을 모르게 하라??
    @GetMapping("/user/{nickname}")
    public String viewProfile(@PathVariable String nickname, Model model) {
        User user = userService.findByNickname(nickname);
        model.addAttribute("userProfile", user);
        return "/user/profile";
    }

    @GetMapping("/user/{nickname}/form")
    public String viewProfileForm(@PathVariable String nickname, Model model) {
        User user = userService.findByNickname(nickname);
        model.addAttribute("user", user);
        return "/user/updateForm";
    }

    @PutMapping("/user/{nickname}")
    public String updateProfileForm(@PathVariable String nickname, User updatedUser) {
        User user = userService.findByNickname(nickname);
        userService.update(user, updatedUser);
        return "redirect:/user/list";
    }
}
