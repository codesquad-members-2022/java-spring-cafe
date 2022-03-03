package com.kakao.cafe.controller;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.service.UserService;
import com.kakao.cafe.service.VolatilityUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/create")
    public String signUp(User user) {
        userService.addUser(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public ModelAndView getUserList(ModelAndView mav) {
        mav.setViewName("user/list");
        mav.addObject("users", userService.findAll());
        return mav;
    }

    @GetMapping("/users/{userId}")
    public ModelAndView getUserProfile(@PathVariable String userId, ModelAndView mav) {
        mav.setViewName("user/profile");
        mav.addObject("user", userService.findUser(userId));
        return mav;
    }
}
