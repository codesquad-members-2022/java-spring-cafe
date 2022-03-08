package com.kakao.cafe.controller;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.domain.dto.UserForm;
import com.kakao.cafe.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/join")
    public String createForm() {
        return "user/form";
    }

    @PostMapping("/join")
    public String create(@Validated UserForm userForm){
        User user = new User(
                userForm.getUserId(),
                userForm.getName(),
                userForm.getPassword(),
                userForm.getEmail()
        );
        userService.join(user);
        return "redirect:/users";
    }

    @GetMapping
    public String list(Model model){
        List<User> users = userService.findUsers();
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/{id}")
    public String profile(@PathVariable("id") int id, Model model) {
        User user = userService.findOneUser(id).get();
        model.addAttribute("user", user);
        return "user/profile";
    }
}
