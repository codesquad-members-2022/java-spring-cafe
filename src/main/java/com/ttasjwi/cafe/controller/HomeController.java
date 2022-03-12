package com.ttasjwi.cafe.controller;

import com.ttasjwi.cafe.domain.Article;
import com.ttasjwi.cafe.service.ArticleService;
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

    @GetMapping
    public String home(Model model) {
        List<Article> articleList = articleService.findArticles();
        model.addAttribute("articles", articleList);
        return "home";
    }
}
