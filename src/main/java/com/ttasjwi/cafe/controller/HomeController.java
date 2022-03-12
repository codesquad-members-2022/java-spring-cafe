package com.ttasjwi.cafe.controller;

import com.ttasjwi.cafe.domain.Article;
import com.ttasjwi.cafe.repository.ArticleRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final ArticleRepository articleRepository;

    public HomeController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @GetMapping
    public String home(Model model) {
        List<Article> articleList = articleRepository.findAll();
        model.addAttribute("articles", articleList);
        return "home";
    }
}
