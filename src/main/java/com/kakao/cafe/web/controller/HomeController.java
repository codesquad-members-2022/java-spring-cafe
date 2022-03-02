package com.kakao.cafe.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("")
    public String home() {
        return "index";
    }
}
