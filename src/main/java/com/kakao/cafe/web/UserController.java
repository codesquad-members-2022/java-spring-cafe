package com.kakao.cafe.web;

import com.kakao.cafe.service.UserService;
import com.kakao.cafe.web.dto.UserDto;
import com.kakao.cafe.web.dto.UserResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/create")
    public String joinForm() {
        return "form";
    }

    @PostMapping("/user/create")
    public String joinUser(UserDto userDto) {
        userService.join(userDto.toEntity());

        return "redirect:/users";
    }

    @GetMapping("/users")
    public String showUsersAll(Model model) {
        List<UserResponseDto> userResponseDtos = userService.findAll();
        model.addAttribute("users", userResponseDtos);

        return "list";
    }

}
