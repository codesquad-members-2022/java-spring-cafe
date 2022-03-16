package com.ttasjwi.cafe.controller;

import com.ttasjwi.cafe.domain.article.Article;
import com.ttasjwi.cafe.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;
    private final Logger log = LoggerFactory.getLogger(getClass());

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/new")
    public String createArticleForm() {
        return "/articles/createArticleForm";
    }

    @PostMapping("/new")
    public String createArticle(@ModelAttribute Article article) {
        article.setWriter("anonymous User");
        article.setRegDateTime(LocalDateTime.now());

        articleService.saveArticle(article);

        log.info("New Article Created: {}", article);
        return "redirect:/";
    }

    @GetMapping("/{articleId}")
    public String showArticle(@PathVariable Long articleId, Model model) {
        Article findArticle = articleService.findOne(articleId);
        model.addAttribute("article", findArticle);
        return "/articles/articleShow";
    }

    @GetMapping
    public String list() {
        return "redirect:/";
    }

}
