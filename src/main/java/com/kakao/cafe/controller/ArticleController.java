package com.kakao.cafe.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.kakao.cafe.domain.article.Article;
import com.kakao.cafe.service.ArticleService;

@Controller
public class ArticleController {

    private final Logger log = LoggerFactory.getLogger(ArticleController.class);

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/")
    public String index(Model model) {
        log.info("GET /");
        model.addAttribute("articles", articleService.showAllArticles());
        return "index";
    }

    @PostMapping("/questions")
    public String addQuestion(Article article) {
        log.info("POST /questions");
        articleService.save(article);
        return "redirect:/";
    }

    @GetMapping("/articles/{index}")
    public String showArticles(@PathVariable int index, Model model) {
        log.info("GET /articles/{}", index);
        model.addAttribute("article", articleService.showArticle(index));
        return "qna/showQna";
    }
}
