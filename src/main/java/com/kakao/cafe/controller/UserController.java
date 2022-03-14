package com.kakao.cafe.controller;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/new")
    public String createForm() {
        return "/user/form";
    }

    @PostMapping("/new")
    public String create(UserForm form) {
        User user = new User(form.getName(), form.getEmail(), form.getPassword());
        userService.join(user);
        return "redirect:/users";
    }

    @GetMapping
    public String list(Model model) {
        List<User> users = userService.findUsers();
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/{name}")
    public String profile(@PathVariable String name, Model model) {
        User user = userService.findUser(name);
        model.addAttribute("user", user);
        return "/user/profile";
    }
}
