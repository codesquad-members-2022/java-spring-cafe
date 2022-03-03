package com.kakao.cafe.controller;

import com.kakao.cafe.entity.Article;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/questions")
public class ArticleController {

    private final Logger log = LoggerFactory.getLogger(ArticleController.class);

    @PostMapping("/")
    public String generateQuestions(@ModelAttribute Article article) {
        return "redirect:/";
    }

}
