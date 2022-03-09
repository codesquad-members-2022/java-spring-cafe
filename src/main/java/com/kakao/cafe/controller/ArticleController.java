package com.kakao.cafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ArticleController {

    @GetMapping("/questions")
    public String createArticle() {
        return "qna/form";
    }
}
