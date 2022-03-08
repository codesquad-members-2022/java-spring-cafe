package com.kakao.cafe.controller;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {this.articleService = articleService;}

    @PostMapping("/qna/create")
    public String createArticle(HttpServletRequest request) {
        Article article = new Article(request.getParameter("writer"),
                                      request.getParameter("title"),
                                      request.getParameter("contents"));

        articleService.createArticle(article);

        return "redirect:/";
    }

    @GetMapping("qna/form")
    public String writeArticle() {
        return "qna/form";
    }

//    @GetMapping("/")
//    public String mainPage() {
//        return
//    }

}
