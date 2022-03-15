package com.kakao.cafe.controller;

import com.kakao.cafe.domain.user.User;
import com.kakao.cafe.domain.user.UserDto;
import com.kakao.cafe.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
public class UserController {


    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String searchUserList(Model model) {
        List<User> users = userService.findAllUsers();
        model.addAttribute("users", users);

        return "user/list";
    }

    @GetMapping("/users/{userId}")
    public String profile(@PathVariable("userId") String userId, Model model) {
        User user = userService.findSingleUser(userId);
        model.addAttribute("user", user);

        return "user/profile";
    }

    @PostMapping("/user/join")
    public String create(UserDto userDto) {
        userService.createUser(userDto);
        return "redirect:/users";
    }

    @ExceptionHandler(IllegalStateException.class)
    public String createFailed(IllegalStateException e, Model model) {
        model.addAttribute("error", e.getMessage());
        return "user/form";
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String searchFailed(NoSuchElementException e, Model model) {
        model.addAttribute("error", e.getMessage());
        return "error/notfound";
    }
}
