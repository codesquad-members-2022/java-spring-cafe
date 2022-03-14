package com.kakao.cafe.controller;

import com.kakao.cafe.controller.dto.UserDto;
import com.kakao.cafe.controller.dto.SignUpRequestDto;
import com.kakao.cafe.controller.dto.UserUpdateRequestDto;
import com.kakao.cafe.service.UserService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public String signUp(SignUpRequestDto form) {
        userService.signUp(form);
        return "redirect:/users";
    }

    @GetMapping
    public String users(Model model) {
        List<UserDto> users = userService.findUsers();
        model.addAttribute("users", users);
        model.addAttribute("totalUserCount", users.size());
        return "user/list";
    }

    @GetMapping("/{userId}")
    public String profile(@PathVariable String userId, Model model) {
        UserDto userDto = userService.findUser(userId);
        model.addAttribute("user", userDto);
        return "user/profile";
    }

    @GetMapping("/{userId}/form")
    public String updateUserForm(@PathVariable String userId, Model model) {
        UserDto userDto = userService.findUser(userId);
        model.addAttribute("user", userDto);
        return "user/updateForm";
    }

    @PostMapping("/{userId}/update")
    public String updateUser(UserUpdateRequestDto form) {
        userService.updateUser(form);
        return "redirect:/users";
    }
}
