package com.kakao.cafe.web;

import com.kakao.cafe.service.UserService;
import com.kakao.cafe.web.dto.UserProfileDto;
import com.kakao.cafe.web.dto.UserRegisterFormDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public String create(UserRegisterFormDto userRegisterFormDto) {
        log.info("[LOG] user request to sign up : {}", userRegisterFormDto);
        userService.register(userRegisterFormDto);
        return "redirect:users";
    }

    @GetMapping("/users")
    public String list(Model model) {
        log.info("[LOG] request to show user list");
        model.addAttribute("users",userService.showAll());
        return "user/list";
    }

    @GetMapping("/users/{userId}")
    public String show(@PathVariable String userId, Model model) {
        log.info("[LOG] request to show {}'s profile", userId);
        UserProfileDto userProfileDto = userService.showOne(userId);
        model.addAttribute("userId", userProfileDto.getUserId());
        model.addAttribute("email", userProfileDto.getEmail());
        return "user/profile";
    }
}
