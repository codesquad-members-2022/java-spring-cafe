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

    @GetMapping("/article")
    public String quaForm() {
        return "qna/form";
    }

    @PostMapping("/article")
    public String createQuestion(ArticleForm form) {
        Article article = new Article(form.getWriter(), form.getTitle(), form.getContents());
        articleService.saveArticle(article);
        return "redirect:/";
    }

    @GetMapping("/article/{index}")
    public String loadOneArticle(@PathVariable Long index, Model model) {
        Article article = articleService.loadOneArticle(index);
        model.addAttribute("article", article);
        return "qna/show";
    }

}
