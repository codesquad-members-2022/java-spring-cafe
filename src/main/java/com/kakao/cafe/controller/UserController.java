package com.kakao.cafe.controller;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.service.UserService;
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
    public String createForm() {
        return "/user/form";
    }

    @PostMapping("/user/create")
    public String create(UserForm form) {
        User user = new User(form);
        userService.join(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String viewList(Model model) {
        List<User> userList = this.userService.getAllUsers();
        model.addAttribute("users", userList);
        return "../templates/list";
    }


}
