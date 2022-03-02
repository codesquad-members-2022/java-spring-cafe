package com.kakao.cafe.web;

import com.kakao.cafe.service.UserService;
import com.kakao.cafe.web.dto.UserRegisterFormDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/form")
    public String createForm() {
        return "user/form";
    }

    @PostMapping("/users")
    public String create(UserRegisterFormDto userSignUpFormDto) {
        log.info("[LOG] user request to sign up : {}", userSignUpFormDto);
        userService.register(userSignUpFormDto);
        return "redirect:users";
    }

    @GetMapping("/users")
    public String list(Model model) {
        log.info("[LOG] request to show user list");
        model.addAttribute("users",userService.showAllUsers());
        return "user/list";
    }
}
