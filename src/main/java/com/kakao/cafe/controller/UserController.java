package com.kakao.cafe.controller;

import com.kakao.cafe.dto.UserRequestDto;
import com.kakao.cafe.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String getUserList(Model model) {
        logger.info("GET /users");
        model.addAttribute("users", userService.findAll());

        return "/user/list";
    }

    @GetMapping("/users/{userId}")
    public String getUserProfile(@PathVariable String userId, Model model) {
        logger.info("GET /users/{}", userId);
        model.addAttribute("user", userService.findOne(userId));

        return "/user/profile";
    }

    @GetMapping("/users/{id}/form")
    public String updateForm(@PathVariable String id, Model model) {
        logger.info("GET /users/{}/form", id);
        model.addAttribute("user", userService.findOne(id));

        return "/user/updateForm";
    }

    @PostMapping("/users")
    public String createUser(UserRequestDto userRequestDto) {
        logger.info("POST /users {}", userRequestDto);
        userService.join(userRequestDto);

        return "redirect:/users";
    }

    @PutMapping("/users/{id}/update")
    public String updateUser(@PathVariable String id, UserRequestDto userRequestDto) {
        logger.info("PUT /users {}", userRequestDto);
        userService.update(id, userRequestDto);

        return "redirect:/users";
    }
}
