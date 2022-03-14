package com.kakao.cafe.controller;

import com.kakao.cafe.controller.dto.ArticleDto;
import com.kakao.cafe.controller.dto.PostingRequestDto;
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

    @PostMapping("/articles")
    public String posting(PostingRequestDto form) {
        articleService.posting(form);
        return "redirect:/";
    }

    @GetMapping("/articles/{id}")
    public String post(@PathVariable int id, Model model) {
        ArticleDto articleDto = articleService.read(id);
        model.addAttribute("article", articleDto);
        return "post/show";
    }
}
