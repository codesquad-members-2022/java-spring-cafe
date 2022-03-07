package com.kakao.cafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/form")
    public String form() {
        return "user/form";
    }

    @GetMapping("/qnaForm")
    public String qnaForm() {
        return "qna/formQna";
    }
}
