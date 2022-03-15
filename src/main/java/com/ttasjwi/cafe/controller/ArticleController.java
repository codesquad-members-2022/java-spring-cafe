package com.ttasjwi.cafe.controller;

import com.ttasjwi.cafe.domain.Article;
import com.ttasjwi.cafe.repository.ArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleRepository articleRepository;
    private final Logger log = LoggerFactory.getLogger(getClass());

    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @GetMapping("/new")
    public String createArticleForm() {
        return "/articles/createArticleForm";
    }

    @PostMapping("/new")
    public String createArticle(@ModelAttribute Article article) {
        article.setWriter("anonymous User");
        article.setRegDateTime(LocalDateTime.now());

        int articleId = articleRepository.save(article);

        log.info("New Article Created: articleId={}, {}", articleId , article);
        return "redirect:/";
    }

    @GetMapping("/{articleId}")
    public String showArticle(@PathVariable int articleId, Model model) {
        Article findArticle = articleRepository.findByArticleId(articleId);
        model.addAttribute("article", findArticle);
        return "/articles/articleShow";
    }

    @GetMapping
    public String list() {
        return "redirect:/";
    }

}
