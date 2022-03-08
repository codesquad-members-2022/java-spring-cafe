package com.kakao.cafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.service.ArticleService;

@Controller
public class ArticleController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/")
    public String list(Model model) {
        model.addAttribute("articles", articleService.findArticles());

        return "/index";
    }

    @PostMapping("/questions")
    public String create(Article article) {
        articleService.add(article);

        return "redirect:/";
    }

    @GetMapping("/articles/{index}")
    public String view(@PathVariable int index, Model model) {
        Article article = articleService.findById(index);
        model.addAttribute("article", article);

        return "qna/show";
    }
}
