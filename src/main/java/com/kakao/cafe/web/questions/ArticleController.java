package com.kakao.cafe.web.questions;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.service.ArticleService;
import com.kakao.cafe.web.users.dto.ArticleDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/questions")
public class ArticleController {

    private final ArticleService articleService;
    private final Logger log = LoggerFactory.getLogger(ArticleController.class);


    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public String requestForm() {
        log.info("create form 접근");
        return "/qna/form";
    }

    @PostMapping
    public String saveForm(@ModelAttribute ArticleDto dto) {
        Article article = new Article(dto.getUserId(), dto.getTitle(), dto.getContent(), LocalDateTime.now());
        articleService.addArticle(article);
        log.info("save form = {}", article.getTitle());
        return "redirect:/";
    }
}
