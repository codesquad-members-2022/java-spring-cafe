package com.kakao.cafe.controller;

import com.kakao.cafe.controller.dto.UserJoinRequestDto;
import com.kakao.cafe.controller.dto.UserUpdateRequestDto;
import com.kakao.cafe.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String userList(Model model) {
        model.addAttribute("users", userService.findUsers());
        return "user/list";
    }

    @GetMapping("/users/join")
    public String joinPage() {
        return "user/form";
    }

    @PostMapping("/users/create")
    public String join(UserJoinRequestDto requestDto) {
        log.info("UserJoinRequestDto: {}", requestDto);
        userService.join(requestDto);
        return "redirect:/users";
    }

    @GetMapping("/users/{userId}")
    public String profile(@PathVariable String userId, Model model) {
        model.addAttribute("user", userService.findByUserId(userId));
        return "user/profile";
    }

    @GetMapping("/users/{userId}/form")
    public String updatePage(@PathVariable String userId, Model model) {
        model.addAttribute("user", userService.findByUserId(userId));
        return "user/updateForm";
    }

    @PostMapping("/users/{userId}/update")
    public String update(@PathVariable String userId, @ModelAttribute UserUpdateRequestDto dto) {
        userService.update(dto);
        return "redirect:/users";
    }
}
