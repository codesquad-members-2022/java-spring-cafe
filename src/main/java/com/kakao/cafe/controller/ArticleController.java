package com.kakao.cafe.controller;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("/questions")
    public ModelAndView create(@RequestParam String writer,
        @RequestParam String title,
        @RequestParam String content) {

        Article article = new Article(writer, title, content);
        Article savedArticle = articleService.write(article);

        ModelAndView mav = new ModelAndView("redirect:/");
        mav.addObject("article", savedArticle);
        return mav;
    }

    @GetMapping
    public String list() {
        return "qna/show";
    }

}
