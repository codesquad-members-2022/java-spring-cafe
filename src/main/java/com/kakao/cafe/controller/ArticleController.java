package com.kakao.cafe.controller;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;

@Controller
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/qna/question")
    public String quaForm() {
        return "qna/form";
    }

    @PostMapping("/qna/question")
    public String createQuestion(ArticleForm form) {
        Article article = new Article(form.getWriter(), form.getTitle(), form.getContents(), LocalDateTime.now());
        articleService.saveArticle(article);
        return "redirect:/";
    }

    @GetMapping("/articles/{index}")
    public String loadOneArticle(@PathVariable int index, Model model) {
        Article article = articleService.loadOneArticle(index);
        model.addAttribute("article", article);
        return "qna/show";
    }

}
