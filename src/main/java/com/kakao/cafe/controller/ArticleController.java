package com.kakao.cafe.controller;

import com.kakao.cafe.controller.dto.ArticleSaveRequestDto;
import com.kakao.cafe.domain.article.Article;
import com.kakao.cafe.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/qna")
    public String articlePage() {
        return "qna/form";
    }

    @PostMapping("/questions")
    public String save(ArticleSaveRequestDto requestDto) {
        articleService.save(requestDto);
        return "redirect:/";
    }

    @GetMapping("/articles/{id}")
    public String articleDetail(@PathVariable Long id, Model model) {
        Article article = articleService.findById(id);
        model.addAttribute("article", article);
        return "qna/show";
    }
}
