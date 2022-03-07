package com.kakao.cafe.controller;

import com.kakao.cafe.domain.user.Users;
import com.kakao.cafe.service.users.UserService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/signup")
    public String signup(){
        return "user/form";
    }

    @PostMapping(path = "users/signup")
    public String save(Users user) {
        userService.save(user);

        return "redirect:list";
    }

    @GetMapping("/users/list")
    public String list(Model model) {
        List<Users> userList = userService.list();
        model.addAttribute("userList", userList);
        return "user/list";
    }
}
