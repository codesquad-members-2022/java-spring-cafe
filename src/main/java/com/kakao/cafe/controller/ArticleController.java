package com.kakao.cafe.controller;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/questions")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public String createForm() {
        return "../templates/form";
    }

    @PostMapping
    public String create(ArticleForm form) {
        Article article = new Article(form);
        articleService.create(article);
        return "redirect:/";
    }

    @GetMapping("/{articleId}")
    public String viewArticle(@PathVariable("articleId") int articleId, Model model) {
        Article selectedArticle;
        Optional<Article> foundArticle = articleService.findById(articleId);
        if (foundArticle.isPresent()) {
            selectedArticle = foundArticle.get();
            model.addAttribute("article", selectedArticle);
            return "/qna/show";
        }
        return null; // 잘못된 접근 페이지 리다이렉트
    }


}
