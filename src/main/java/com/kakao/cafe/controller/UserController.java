package com.kakao.cafe.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.kakao.cafe.domain.user.User;
import com.kakao.cafe.domain.user.dto.UserDto;
import com.kakao.cafe.domain.user.dto.UserProfileDto;
import com.kakao.cafe.domain.user.exception.DuplicatedIdException;
import com.kakao.cafe.service.UserService;

@Controller
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public String addUser(User user) {
        logger.info("POST /users");
        userService.save(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String showUsers(Model model) {
        logger.info("GET /users");
        List<UserDto> userDtoList = userService.findAllUser();
        model.addAttribute("users", userDtoList);
        return "list";
    }

    @GetMapping("/users/{userId}")
    public String showUserProfile(@PathVariable String userId, Model model) {
        logger.info("GET /users/{}", userId);
        UserProfileDto userProfileDto = userService.findUserProfileByUserId(userId);
        model.addAttribute("userProfile", userProfileDto);
        return "/user/profile";
    }

    @GetMapping("/users/{userId}/check")
    public String verifyPassword(@PathVariable String userId, Model model) {
        logger.info("GET /users/{}/check", userId);
        model.addAttribute("userId", userId);
        return "/user/passwordCheck";
    }

    @PostMapping("/users/{userId}/form")
    public String editMemberInformation(@PathVariable String userId, Model model) {
        logger.info("GET /users/{}/form", userId);
        User user = userService.findUserByUserId(userId);
        model.addAttribute("user", user);
        return "/user/updateForm";
    }
}
