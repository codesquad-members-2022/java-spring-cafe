package com.kakao.cafe.controller;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ArticleController {

    private ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/")
    public String list(Model model) {
        List<Article> articles = articleService.findAll();
        model.addAttribute("articles" , articles);
        return "qna/list";
    }

    @GetMapping("/qna/form")
    public String form() {
        return "qna/form";
    }

    @PostMapping("/qna/form")
    public String create(Article article) {
        articleService.write(article);
        return "redirect:/";
    }

}
