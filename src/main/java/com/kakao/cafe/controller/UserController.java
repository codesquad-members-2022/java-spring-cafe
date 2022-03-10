package com.kakao.cafe.controller;

import com.kakao.cafe.entity.User;
import com.kakao.cafe.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/list")
    public String findAllUser(Model model) {
        log.info("유저 리스트 조회 시도");
        List<User> users = userService.findUsers();
        model.addAttribute("users", users);
        log.info("유저 리스트 조회 성공 유저 수={}", users.size());
        log.debug("유저리스트={}", users);
        return "user/list";
    }

    @PostMapping("/create")
    public String users(@ModelAttribute User user) {
        log.info("유저 회원가입 시도");
        userService.signUp(user);
        log.info("{} 이메일을 가지는 유저 회원가입 성공", user.getEmail());
        log.debug("회원가입={}", user);
        return "redirect:list";
    }

    @GetMapping("/profile/{userId}")
    public String profile(@PathVariable String userId, Model model) {
        log.info("유저 프로필 조회 시도");
        User findUser = userService.findIdUser(userId);
        model.addAttribute("user", findUser);
        log.info("{} 유저 프로필 조회 성공", findUser.getUserId());
        log.debug("프로필조회={}", findUser);
        return "user/profile";
    }
}
