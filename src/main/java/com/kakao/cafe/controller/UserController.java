package com.kakao.cafe.controller;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.servcie.UserService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/form")
    public String joinForm(){
        return "user/form";
    }

    @PostMapping("/users")
    public String join(@ModelAttribute User user){
        userService.join(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String list(Model model){
        List<User> users = userService.findUsers();
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/user/{nickname}")
    public String profile(@PathVariable String nickname, Model model){
        model.addAttribute("user", userService.findByNickname(nickname).get());
        return "user/profile";
    }
}
