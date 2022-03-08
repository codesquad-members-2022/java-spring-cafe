package com.kakao.cafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ArticleController {

    @PostMapping
    public String createArticle(HttpServletRequest request) {

        return "qna/form";
    }

}
