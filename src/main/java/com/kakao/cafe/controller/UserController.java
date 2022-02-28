package com.kakao.cafe.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.kakao.cafe.domain.users.User;
import com.kakao.cafe.domain.users.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class UserController {

    private final UserRepository userRepository = new UserRepository(new ArrayList<>());

    @GetMapping("/users")
    public void showUsers() {

    }

    @PostMapping("/users")
    public String addUser(@ModelAttribute User user) {
        log.info("username = {}, email = {}", user.getName(), user.getEmail());
        userRepository.save(user);
        return "redirect:/users";
    }
}
