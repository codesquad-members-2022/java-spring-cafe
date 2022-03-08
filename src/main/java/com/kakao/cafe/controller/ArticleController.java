package com.kakao.cafe.controller;

import com.kakao.cafe.entity.Article;
import com.kakao.cafe.service.ArticleService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/questions")
public class ArticleController {

    private final ArticleService articleService;
    private final Logger log = LoggerFactory.getLogger(ArticleController.class);

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("/new")
    public String generateQuestions(@ModelAttribute Article article) {
        articleService.generateArticle(article);
        log.info("작성 성공 = {}", article.getTitle());
        return "redirect:/";
    }

    @GetMapping("/{articleId}")
    public String viewDetails(@PathVariable int articleId, Model model) {
        Article findArticle = articleService.findArticleById(articleId);
        log.info("findArticle = {}", findArticle);
        model.addAttribute("article", findArticle);
        return "qna/show";
    }
}
