package com.kakao.cafe.controller;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/questions/form")
    public String createForm() {
        return "qna/form";
    }

    @PostMapping("/questions/form")
    public String createArticle(ArticleForm articleForm) {
        articleService.write(articleForm);
        return "redirect:/";
    }

    @GetMapping("/articles/{index}")
    public String viewArticle(@PathVariable int index, Model model) {
        Article article = articleService.findArticle(index);
        model.addAttribute("article", article);
        return "qna/show";
    }
}
