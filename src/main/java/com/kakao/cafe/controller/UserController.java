package com.kakao.cafe.controller;

import com.kakao.cafe.controller.userdto.UserCreateDto;
import com.kakao.cafe.domain.User;
import com.kakao.cafe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String homePage() {
        return "home/index";
    }

    @GetMapping("/user/create")
    public String createForm() {
        return "/user/create";
    }

    @PostMapping("/user/create")
    public String createUser(UserCreateDto userCreateDto) {
        User user = new User(
                userCreateDto.getUserName(),
                userCreateDto.getUserPassword(),
                userCreateDto.getUserEmail()
        );

        userService.createUser(user);

        // 아래 @GetMapping("/user/list") 컨트롤러 호출을 위해 redirect 사용
        return "redirect:/user/list";
    }

    @GetMapping("/user/list")
    public String userList(Model model) {
        List<User> users = userService.findAllUsers();
        model.addAttribute("users", users);

        return "/user/list";
    }

    @GetMapping("/user/{userName}")
    public String userProfile(@PathVariable String userName, Model model) {
        Optional<User> user = userService.findUser(userName);
        user.ifPresent(foundUser ->
                model.addAttribute("foundUser", foundUser));

        return "/user/profile";
    }
}
