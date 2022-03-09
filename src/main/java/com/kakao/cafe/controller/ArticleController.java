package com.kakao.cafe.controller;

import com.kakao.cafe.domain.Article;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class ArticleController {
    @GetMapping("/questions")
    public String createForm() {
        return "../templates/form";
    }

    @PostMapping("/questions")
    public String create(ArticleForm form) {
        Article article = new Article(form);
        articleService.create(article);
        return "redirect:/";
    }

    @GetMapping("/articles/{articleId}")
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
