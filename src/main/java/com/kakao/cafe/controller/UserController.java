package com.kakao.cafe.controller;

import java.util.List;

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

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public String addUser(User user) {
        userService.save(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String showUsers(Model model) {
        List<UserDto> userDtoList = userService.findAllUser();
        model.addAttribute("users", userDtoList);
        return "list";
    }

    @GetMapping("/users/{userId}")
    public String showUserProfile(@PathVariable String userId, Model model) {
        UserProfileDto userProfileDto = userService.findUserByUserId(userId);
        model.addAttribute("userProfile", userProfileDto);
        return "user/profile";
    }

    @ExceptionHandler(DuplicatedIdException.class)
    public String duplicatedException(Exception e) {
        System.out.println(e.getMessage());
        return "duplicated_error";
    }
}
