package com.kakao.cafe.controller;

import com.kakao.cafe.dto.UserRequestDto;
import com.kakao.cafe.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String getUserList(Model model) {
        model.addAttribute("users", userService.findAll());

        return "user/list";
    }

    @GetMapping("/users/{userId}")
    public String getUserProfile(@PathVariable String userId, Model model) {
        model.addAttribute("user", userService.findOne(userId));

        return "user/profile";
    }

    @GetMapping("/users/{id}/form")
    public String updateForm(@PathVariable String id, Model model) {
        model.addAttribute("user", userService.findOne(id));

        return "user/updateForm";
    }

    @PostMapping("/users")
    public String createUser(UserRequestDto userRequestDto) {
        userService.join(userRequestDto);

        return "redirect:/users";
    }

    @PutMapping("/users/{id}/update")
    public String updateUser(@PathVariable String id, UserRequestDto userRequestDto) {
        userService.update(id, userRequestDto);

        return "redirect:/users";
    }
}
