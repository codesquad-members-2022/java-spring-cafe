package com.kakao.cafe.controller;

import com.kakao.cafe.controller.userdto.UserCreateDto;
import com.kakao.cafe.domain.User;
import com.kakao.cafe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String homePage() {
        return "home/index";
    }

    @GetMapping("/user/create")
    public String createForm() {
        return "/user/create";
    }

    @PostMapping("/user/create")
    public String createUser(User userCreateDto) {
        userService.createUser(userCreateDto);

        return "redirect:/user/list";
    }

    @GetMapping("/user/list")
    public String userList(Model model) {
        List<User> users = userService.findAllUsers();
        model.addAttribute("users", users);

        return "user/list";
    }

    @GetMapping("/user/{userName}")
    public String userProfile(@PathVariable String userName, Model model) {
        userService.findUser(userName)
                .ifPresentOrElse(user -> {
                    model.addAttribute("foundUser", user);
                }, () -> {
                    throw new IllegalStateException("찾을 수 없는 유저입니다.");
                });

        return "user/profile";
    }
}
