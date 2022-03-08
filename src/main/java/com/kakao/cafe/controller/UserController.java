package com.kakao.cafe.controller;

import com.kakao.cafe.domain.user.User;
import com.kakao.cafe.domain.user.UserForm;
import com.kakao.cafe.service.user.UserService;
import com.kakao.cafe.service.user.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/join")
    public String joinForm() {
        return "user/form";
    }

    @GetMapping("/users")
    public String list(Model model) {
        List<User> users = userService.findAllUsers();
        model.addAttribute("users", users);

        return "user/list";
    }

    @GetMapping("/users/{userId}")
    public String profile(@PathVariable("userId") String userId, Model model) {
        User user = userService.findSingleUser(userId).get();
        model.addAttribute("user", user);

        return "user/profile";
    }

    @PostMapping("/user/join")
    public String create(UserForm userForm) {
        userService.createUser(userForm);
        return "redirect:/users";
    }

    @ExceptionHandler(IllegalStateException.class)
    public String createFailed(IllegalStateException e, Model model) {
        model.addAttribute("error", e.getMessage());
        return "user/form";
    }
}
