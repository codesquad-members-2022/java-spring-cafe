package com.kakao.cafe.controller;

import com.kakao.cafe.domain.article.Article;
import com.kakao.cafe.domain.article.ArticleDto;
import com.kakao.cafe.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {

    private final ArticleService service;

    public ArticleController(ArticleService service) {
        this.service = service;
    }

    @PostMapping("/questions/")
    public String createArticle(ArticleDto articleDto) {
        service.createArticle(articleDto);
        return "redirect:/";
    }

    @GetMapping("/questions/form")
    public String create() {
        return "qna/form";
    }

    @GetMapping("/articles/{index}")
    public String detailArticlePage(@PathVariable Long index, Model model) {
        ArticleDto article = service.findSingleArticle(index);
        model.addAttribute("article", article);
        return "qna/show";
    }

}
