package com.kakao.cafe.controller;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.controller.dto.UserDto;
import com.kakao.cafe.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/users")
@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String userList(Model model) {
        List<User> users = userService.findUsers();
        model.addAttribute("users", users);
        return "user/list";
    }

    @PostMapping
    public String createUser(UserDto userDto) {
        User user = userDto.createUser();
        userService.join(user);
        return "redirect:/users";
    }

    @GetMapping("/{userId}")
    public String userProfile(@PathVariable Long userId, Model model) {
        User user = userService.findOne(userId);
        model.addAttribute("user", user);
        return "user/profile";
    }

}
