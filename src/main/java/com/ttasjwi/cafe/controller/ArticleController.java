package com.ttasjwi.cafe.controller;

import com.ttasjwi.cafe.domain.Article;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

@Controller
@RequestMapping("/articles")
public class ArticleController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping("/new")
    public String createArticleForm() {
        return "/articles/createArticleForm";
    }

    @PostMapping("/new")
    public String createArticle(@ModelAttribute Article article) {
        article.setWriter("anonymous User");
        article.setRegDate(LocalDate.now());
        log.info("new Article={}",article);
        return "redirect:/";
    }

}
