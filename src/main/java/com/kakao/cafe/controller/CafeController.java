package com.kakao.cafe.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CafeController {

    Logger logger = LoggerFactory.getLogger(CafeController.class);

    @GetMapping("/users")
    public String helloUser(Model model) {
        logger.info("GET /users");
        return "users";
    }

}
