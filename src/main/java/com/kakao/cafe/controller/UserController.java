package com.kakao.cafe.controller;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/create")
    public String joinForm() {
        return "user/form";
    }

    @PostMapping("/user/create")
    public ModelAndView joinUser(UserForm form) {
        User user = new User(form);
        userService.join(user);

        ModelAndView modelAndView = new ModelAndView("redirect:/list/show");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @GetMapping("/list/show")
    public String allUsers(Model model) {
        List<User> users = userService.findUsers();
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/user/{userId}")
    public String profile(@PathVariable String userId, Model model) {
        User user = userService.findOne(userId);
        model.addAttribute("userId", user.getUserId());
        model.addAttribute("email", user.getEmail());
        return "user/profile";
    }

    @GetMapping("/user/{userId}/form")
    public String updateForm(@PathVariable String userId, Model model) {
        model.addAttribute("userId", userId);
        return "user/updateForm";
    }

    @PutMapping("/user/{userId}/form")
    public String updateUser(UserForm userForm) {
        userService.updateUser(userForm);
        return "redirect:/list/show";
    }
}
