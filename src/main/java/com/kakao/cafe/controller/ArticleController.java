package com.kakao.cafe.controller;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {

    private final Logger logger = LoggerFactory.getLogger(ArticleController.class);

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("/questions")
    public String createArticle(Article article) {
        logger.info("POST /questions {}", article);
        articleService.upload(article);

        return "redirect:/";
    }

    @GetMapping("/articles/{index}")
    public String getArticle(@PathVariable("index") int id, Model model) {
        logger.info("GET /articles/{}", id);
        Article article = articleService.findOne(id);
        model.addAttribute("article", article);

        return "/qna/show";
    }
}
