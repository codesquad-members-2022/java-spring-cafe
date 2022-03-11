package com.kakao.cafe.controller;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/users")
    public String create(UserForm form) {
        User user = new User();
        user.setUserId(form.getUserId());
        user.setName(form.getName());
        user.setPassword(form.getPassword());
        user.setEmail(form.getEmail());

        userService.join(user);

        return "redirect:/users";
    }

    @GetMapping("/users")
    public String list(Model model) {
        List<User> users = userService.findMembers();
        model.addAttribute("users", users);
        return "/user/list";
    }


    @GetMapping("/users/{userId}")
    public String userProfile(@PathVariable Long userId, Model model) {
        List<User> members = userService.findMembers();
        User user = members.stream()
                .filter(m -> m.getId().equals(userId))
                .findAny()
                .get();
        model.addAttribute("user", user);
        return "/user/profile";
    }
}
