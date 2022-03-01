package com.kakao.cafe.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("members")
public class HomeController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/{userId}")
    public String home(@PathVariable("userId") String userId) {
        return "index";
    }
}
