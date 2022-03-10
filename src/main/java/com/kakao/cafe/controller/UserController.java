package com.kakao.cafe.controller;

import com.kakao.cafe.controller.dto.UserDto;
import com.kakao.cafe.controller.dto.SignUpRequestDto;
import com.kakao.cafe.service.UserService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public String signUp(SignUpRequestDto form) {
        userService.signUp(form);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String list(Model model) {
        List<UserDto> users = userService.findUsers();
        model.addAttribute("users", users);
        model.addAttribute("totalUserCount", users.size());
        return "user/list";
    }

    @GetMapping("/users/{email}")
    public String profile(@PathVariable String email, Model model) {
        UserDto userDto = userService.findUser(email);
        model.addAttribute("user", userDto);
        return "user/profile";
    }
}
