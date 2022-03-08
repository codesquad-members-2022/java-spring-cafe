package com.kakao.cafe.controller;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.domain.dto.ArticleForm;
import com.kakao.cafe.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/questions")
    public String quest() {
        return "qna/form";
    }

    @PostMapping("/questions")
    public String createQuest(@Validated ArticleForm articleForm) {
        Article article = new Article(
                articleForm.getTitle(),
                articleForm.getWriter(),
                articleForm.getContents()
        );
        articleService.post(article);
        return "redirect:/";
    }

    @GetMapping("articles/{index}")
    public String detail(@PathVariable("index") int index, Model model) {
        Article article = articleService.findOneArticle(index).get();
        model.addAttribute("article", article);
        return "qna/show";
    }
}
