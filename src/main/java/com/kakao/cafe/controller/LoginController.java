package com.kakao.cafe.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    private final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @GetMapping("/login")
    public String requestLoginTemplate() {
        logger.info("GET /login");
        return "login/login";
    }

    @GetMapping("/logout")
    public String requestLogout(@CookieValue(value = "sessionUser") String sessionUser, HttpServletResponse response, HttpSession session) {
        logger.info("GET /logout");
        session.setAttribute(sessionUser, null);
        Cookie cookie = new Cookie("sessionUser", null);
        cookie.setPath("/");
        response.addCookie(cookie);
        return "redirect:/";
    }
}
