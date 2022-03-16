package com.kakao.cafe.controller;

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

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/new")
    public String createForm() { return "user/form"; }

    @PostMapping("/users")
    public String create(UserForm form) {
        User user = new User(form.getEmail(),form.getUserId(),form.getPassward());
        userService.join(user);

        return "redirect:/users";
    }

    @GetMapping("/users")
    public String viewUserList(Model model){
        List<User> users = userService.findUsers();
        model.addAttribute("users",users);
        model.addAttribute("countOfTotalUser",users.size());
        return "user/list";
    }

    @GetMapping("/users/{userId}")
    public String viewUserProfile(@PathVariable String userId, Model model){
        model.addAttribute("user",userService.findOneByUserId(userId).get());
        return "user/profile";
    }
}
