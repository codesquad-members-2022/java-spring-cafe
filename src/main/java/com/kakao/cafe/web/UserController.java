package com.kakao.cafe.web;

import com.kakao.cafe.domain.user.User;
import com.kakao.cafe.service.UserService;
import com.kakao.cafe.web.dto.UserDto;
import com.kakao.cafe.web.dto.UserResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.stream.Collectors;

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
        User user = new User();
        user.setUserId(userDto.getUserId());
        user.setPassword(userDto.getPassword());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());

        userService.join(user);

        return "redirect:/users";
    }

    @GetMapping("/users")
    public String showUsersAll(Model model) {
        List<User> users = userService.findAll();
        List<UserResponseDto> dtos = users.stream().map(UserResponseDto::new).collect(Collectors.toList());
        model.addAttribute("users", dtos);

        return "list";
    }

}
