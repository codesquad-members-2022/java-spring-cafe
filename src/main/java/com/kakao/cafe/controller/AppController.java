package com.kakao.cafe.controller;

import com.kakao.cafe.controller.dto.ArticleDto;
import com.kakao.cafe.service.ArticleService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {

    private final ArticleService articleService;

    public AppController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<ArticleDto> articles = articleService.findPosts();
        model.addAttribute("articles", articles);
        model.addAttribute("totalArticleCount", articles.size());
        return "index";
    }
}
