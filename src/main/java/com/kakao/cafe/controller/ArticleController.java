package com.kakao.cafe.controller;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.domain.dto.ArticleForm;
import com.kakao.cafe.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/questions")
public class ArticleController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public String quest() {
        return "qna/form";
    }

    @PostMapping
    public String createQuest(@Validated ArticleForm articleForm) {
        Article article = new Article(
                articleForm.getTitle(),
                articleForm.getWriter(),
                articleForm.getContents()
        );
        articleService.post(article);
        return "redirect:/";
    }
}
