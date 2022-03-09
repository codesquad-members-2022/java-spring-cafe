package com.kakao.cafe.controller;

import com.kakao.cafe.controller.articledto.ArticleCreateDto;
import com.kakao.cafe.domain.Article;
import com.kakao.cafe.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;
import java.util.Optional;

@Controller
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/article/questions")
    public String createArticleForm() {
        return "/article/questions";
    }

    @PostMapping("/article/questions")
    public String crateArticle(ArticleCreateDto articleCreateDto) {
        Article article = new Article(
                articleCreateDto.getArticleTitle(),
                articleCreateDto.getArticleContent()
        );

        articleService.createArticle(article);

        return "redirect:/";
    }

    @GetMapping("/article/list")
    public String articleList(Model model) {
        List<Article> articles = articleService.findAllArticle();
        model.addAttribute("articles", articles);

        return "/article/list";
    }

    @GetMapping("/article/{articleIdx}")
    public String articleDetail(@PathVariable long articleIdx, Model model) {
        Optional<Article> article = articleService.findArticle(articleIdx);

        article.ifPresent(foundArticle ->
            model.addAttribute("foundArticle", foundArticle)
        );

        return "/article/show";
    }
}
