package com.kakao.cafe.controller;

import com.kakao.cafe.dto.UserArticle;
import com.kakao.cafe.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/articles/{index}")
    public String getUserArticle(@PathVariable Integer index, Model model) {
        logger.info("GET /articles/{}", index);
        UserArticle userArticle = articleService.findOne(index);
        model.addAttribute("article", userArticle);

        return "/qna/show";
    }
}
