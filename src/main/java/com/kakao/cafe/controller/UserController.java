package com.kakao.cafe.controller;

import com.kakao.cafe.controller.dto.UserDto;
import com.kakao.cafe.domain.User;
import com.kakao.cafe.controller.dto.SignUpRequestDto;
import com.kakao.cafe.service.UserService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public String signUp(SignUpRequestDto form) {
        User user = new User();
        user.setEmail(form.getEmail());
        user.setNickname(form.getNickname());
        user.setPassword(form.getPassword());
        userService.signUp(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String list(Model model) {
        List<UserDto> users = new ArrayList<>();
        for (User user : userService.findUsers()) {
            users.add(UserDto.from(user));
        }
        model.addAttribute("users", users);
        model.addAttribute("totalUserCount", users.size());
        return "user/list";
    }

    @GetMapping("/users/{userId}")
    public String profile(@PathVariable Long userId, Model model) {
        User user = userService.findUser(userId);
        UserDto userDto = UserDto.from(user);
        System.out.println(userDto);
        model.addAttribute("user", userDto);
        return "user/profile";
    }
}
