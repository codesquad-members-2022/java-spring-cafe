package com.kakao.cafe.controller.article;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/new")
    public String addArticleForm() {
        return "articles/articleForm";
    }

    @PostMapping("/new")
    public String saveArticleForm(ArticleForm form) {
        Article article = new Article(form.getId(), form.getTitle(), form.getText());
        articleService.join(article);
        return "redirect:/";
    }
}
