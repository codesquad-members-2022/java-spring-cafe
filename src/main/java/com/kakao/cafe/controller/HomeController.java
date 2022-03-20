package com.kakao.cafe.controller;

import com.kakao.cafe.domain.article.ArticleDto;
import com.kakao.cafe.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    private final ArticleService articleService;

    public HomeController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/")
    public String mainPage(Model model) {
        List<ArticleDto> articles = articleService.findAllArticle();
        model.addAttribute("articleList", articles);
        return "index";
    }

}
