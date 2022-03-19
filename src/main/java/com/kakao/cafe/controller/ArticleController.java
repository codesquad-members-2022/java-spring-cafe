package com.kakao.cafe.controller;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.domain.ArticleWriteRequest;
import com.kakao.cafe.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/questions")
    public String formCreateArticle() {
        return "qna/form";
    }

    @PostMapping("/questions")
    public String create(ArticleWriteRequest articleRequest) {
        articleService.write(articleRequest);
        return "redirect:/";
    }

    @GetMapping("/")
    public String articles(Model model) {
        List<Article> articles = articleService.findArticles();
        model.addAttribute("articles", articles);
        return "index";
    }

    @GetMapping("/articles/{articleId}")
    public String article(@PathVariable(value = "articleId") int articleId, Model model) {
        Article article = articleService.findArticle(articleId);
        model.addAttribute("article", article);
        return "qna/show";
    }
}
