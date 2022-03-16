package com.kakao.cafe.controller;

import com.kakao.cafe.service.ArticleService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class HomeController {

    private final ArticleService articleService;

    public HomeController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public ModelAndView getArticles(ModelAndView mav) {
        mav.setViewName("qna/list");
        mav.addObject("articles", articleService.searchAll());
        return mav;
    }
}
