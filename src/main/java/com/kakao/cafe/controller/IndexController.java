package com.kakao.cafe.controller;

import com.kakao.cafe.domain.article.Article;
import com.kakao.cafe.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class IndexController {
    private final ArticleService articleService;

    public IndexController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<Article> articleList = articleService.findAllDesc();
        model.addAttribute("articleList", articleList);
        return "index";
    }
}
