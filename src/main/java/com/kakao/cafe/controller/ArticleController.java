package com.kakao.cafe.controller;

import com.kakao.cafe.domain.dto.ArticleForm;
import com.kakao.cafe.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import javax.validation.Valid;

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
    public String createQuest(@Valid ArticleForm articleForm) {
        articleService.post(articleForm);
        return "redirect:/";
    }

    @GetMapping("articles/{index}")
    public String detail(@PathVariable("index") int index, Model model) {
        ArticleForm articleForm = articleService.findOneArticle(index-1);
        model.addAttribute("article", articleForm);
        return "qna/show";
    }
}
