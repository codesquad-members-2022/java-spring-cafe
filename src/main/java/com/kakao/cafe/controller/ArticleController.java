package com.kakao.cafe.controller;

import com.kakao.cafe.entity.Article;
import com.kakao.cafe.service.ArticleService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/questions")
public class ArticleController {

    private final ArticleService articleService;
    private final Logger log = LoggerFactory.getLogger(ArticleController.class);

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("/new")
    public String registerQuestions(@ModelAttribute Article article) {
        log.info("질문 작성 시도");
        articleService.registerArticle(article);
        log.info("{} 가 {} 질문을 작성했습니다. (작성시간 : {})", article.getWriter(), article.getTitle(), article.getDateOfIssue());
        log.debug("작성 글={}", article);
        return "redirect:/";
    }

    @GetMapping("/{articleId}")
    public String viewDetails(@PathVariable int articleId, Model model) {
        log.info("질문 상세보기 페이지 접속 시도");
        Article findArticle = articleService.findArticleById(articleId);
        model.addAttribute("article", findArticle);
        log.info("{} 질문 상세페이지 접속 성공", findArticle.getTitle());
        log.debug("접속 글={}", findArticle);
        return "qna/show";
    }
}
