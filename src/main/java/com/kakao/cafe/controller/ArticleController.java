package com.kakao.cafe.controller;


import com.kakao.cafe.domain.Article;
import com.kakao.cafe.domain.User;
import com.kakao.cafe.service.ArticleService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ArticleController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/")
    public String list(Model model) {
        List<Article> articles = articleService.findArticles();
        model.addAttribute("articles", articles);
        return "index";
    }

    @PostMapping("questions")
    public String create(ArticleDto requestDto) {
        articleService.add(requestDto);

        return "redirect:/";
    }

//    @RequestMapping(value = "questions/{id}", method = RequestMethod.GET)
    @GetMapping("questions/{id}")
    public String show(@PathVariable("id") int index, Model model) {
        logger.info("index: {}", index);
        Article article = articleService.findOne(index);
        model.addAttribute("article", article);

        return "qna/show";
    }



}
