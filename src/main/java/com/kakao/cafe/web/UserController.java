package com.kakao.cafe.web;

import com.kakao.cafe.CustomLogger;
import com.kakao.cafe.service.UserService;
import com.kakao.cafe.web.dto.UserProfileDto;
import com.kakao.cafe.web.dto.UserRegisterFormDto;

import com.kakao.cafe.web.dto.UserUpdateFormDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

    private static final CustomLogger log = new CustomLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public String create(@ModelAttribute UserRegisterFormDto userRegisterFormDto) {
        log.info("sign up : {}", userRegisterFormDto);
        userService.register(userRegisterFormDto);
        return "redirect:/users";
    }

    @GetMapping
    public String list(Model model) {
        log.info("show user list");
        model.addAttribute("users", userService.showAll());
        return "/user/list";
    }

    @GetMapping("/{userId}")
    public String show(@PathVariable String userId, Model model) {
        log.info("show {}'s profile", userId);
        UserProfileDto userProfileDto = userService.showOne(userId);
        model.addAttribute("userId", userProfileDto.getUserId());
        model.addAttribute("email", userProfileDto.getEmail());
        return "/user/profile";
    }

    @GetMapping("/{userId}/form")
    public String updateForm(@PathVariable String userId, Model model) {
        log.info("show {}'s update form", userId);
        model.addAttribute("userId", userId);
        return "/user/updateForm";
    }

    @PostMapping("/{userId}/update")
    public String update(@PathVariable String userId, UserUpdateFormDto userUpdateFormDto) {
        log.info("update {}'s profile : {}", userId, userUpdateFormDto);
        userService.modify(userUpdateFormDto);
        return "redirect:/users";
    }
}
