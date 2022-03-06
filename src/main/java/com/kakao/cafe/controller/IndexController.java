package com.kakao.cafe.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.kakao.cafe.service.ArticleService;

@Controller
public class IndexController {

    @GetMapping("/form")
    public String form() {
        return "form";
    }

    @GetMapping("/qnaForm")
    public String qnaForm() {
        return "qna/formQna";
    }
}
