package com.kakao.cafe.controller;

import com.kakao.cafe.entity.User;
import com.kakao.cafe.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final Logger log = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signup")
    public String signup() {
        log.info("회원가입 페이지 접속");
        return "user/form";
    }

    @GetMapping("/list")
    public String findAllUser(Model model) {
        List<User> users = userService.findUsers();
        model.addAttribute("users", users);
        log.info("유저 리스트 페이지 접속");
        return "user/list";
    }

    @PostMapping("/create")
    public String users(@ModelAttribute User user) {
        userService.signUp(user);
        log.info("signUp user = {}", user);
        return "redirect:list";
    }
}
