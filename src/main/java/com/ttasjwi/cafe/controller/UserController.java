package com.ttasjwi.cafe.controller;

import com.ttasjwi.cafe.domain.User;
import com.ttasjwi.cafe.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private final Logger log = LoggerFactory.getLogger(getClass());

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/new")
    public String createForm() {
        return "/users/createUserForm";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute User user) {
        userService.join(user);
        log.info("new user={}", user);
        return "redirect:/users";
    }

    @GetMapping("/{userName}")
    public String profile(@PathVariable String userName, Model model) {
        User user = userService.findByUserName(userName);
        model.addAttribute("user", user);
        return "/users/userProfile";
    }

    @GetMapping
    public String list(Model model) {
        List<User> list = userService.findAll();
        model.addAttribute("users", list);
        return "/users/userList";
    }
}
