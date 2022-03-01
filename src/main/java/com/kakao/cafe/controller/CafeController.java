package com.kakao.cafe.controller;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.service.UserService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CafeController {

    Logger logger = LoggerFactory.getLogger(CafeController.class);
    UserService userService = new UserService();

    @PostMapping("/users")
    public String create(User form) {
        User user = new User();
        user.setUserId(form.getUserId());
        user.setName(form.getName());
        user.setEmail(form.getEmail());
        user.setPassword(form.getPassword());

        userService.join(user);

        return "redirect:/users";
    }

    @GetMapping("/users")
    public String list(Model model) {
        List<User> users = userService.findUsers();
        model.addAttribute("users", users);
        System.out.println(users);
        return "/users/list";
    }

    @GetMapping("/test")
    public String list2(Model model) {
        List<User> users = userService.findUsers();
        model.addAttribute("users", users);
        System.out.println(users);
        return "users/list";
    }
}
