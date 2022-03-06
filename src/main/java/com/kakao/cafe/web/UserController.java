package com.kakao.cafe.web;

import com.kakao.cafe.service.UserService;
import com.kakao.cafe.web.dto.UserDto;
import com.kakao.cafe.web.dto.UserResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import javax.validation.Valid;
import java.util.List;


@Controller
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/create")
    public String joinForm() {
        logger.info("user in joinForm");
        return "/user/form";
    }

    @PostMapping("/user/create")
    public String joinUser(@Valid UserDto userDto) {
        logger.info("{} joined", userDto);
        userService.join(userDto.toEntity());

        return "redirect:/users";
    }

    @GetMapping("/users")
    public String showUsersAll(Model model) {
        logger.info("search all users");
        List<UserResponseDto> userResponseDtos = userService.findAll();
        model.addAttribute("users", userResponseDtos);

        return "/user/list";
    }

    @GetMapping("/users/{id}")
    public String showProfile(@PathVariable String id, Model model) {
        logger.info("search {}", id);
        UserResponseDto userResponseDto = userService.findUser(id);
        model.addAttribute("user", userResponseDto);

        return "/user/profile";
    }

    @GetMapping("users/{id}/form")
    public String updateForm(@PathVariable String id, Model model) {
        logger.info("{} in updateForm", id);
        UserResponseDto userResponseDto = userService.findUser(id);
        model.addAttribute("user", userResponseDto);

        return "/user/update_form";
    }

    @PutMapping("users/{id}/update")
    public String updateInfo(@PathVariable String id, UserDto userDto) {
        logger.info("{} update info {}", id, userDto);
        userService.updateUserInfo(userDto.toEntity());

        return "redirect:/users";
    }

}
