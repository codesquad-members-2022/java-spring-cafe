package com.kakao.cafe.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/logout")
public class LogoutController {

    private static final Logger log = LoggerFactory.getLogger(LogoutController.class);

    @GetMapping
    public String logout(HttpSession session) {
        session.removeAttribute("user");

        return "redirect:/";
    }
}
