package com.kakao.cafe.controller;

import com.kakao.cafe.dto.UserArticle;
import com.kakao.cafe.service.ArticleService;
import com.kakao.cafe.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {

    private Logger logger = LoggerFactory.getLogger(ArticleController.class);

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("/questions")
    public String createUserArticle(UserArticle userArticle) {
        logger.info("POST /questions writer = {} title = {} contents = {}",
                    userArticle.getWriter(), userArticle.getTitle(), userArticle.getContents());

        articleService.upload(userArticle);

        return "redirect:/";
    }
}
