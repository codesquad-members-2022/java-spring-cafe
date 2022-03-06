package com.kakao.cafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.kakao.cafe.domain.article.Article;
import com.kakao.cafe.service.ArticleService;

@Controller
public class ArticleController {
    
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("articles", articleService.showArticles());
        return "index";
    }

    @PostMapping("/questions")
    public String addQuestion(Article article) {
        articleService.save(article);
        return "redirect:/";
    }


}
