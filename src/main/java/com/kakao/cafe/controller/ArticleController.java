package com.kakao.cafe.controller;

import com.kakao.cafe.controller.dto.ArticleDto;
import com.kakao.cafe.domain.Article;
import com.kakao.cafe.domain.User;
import com.kakao.cafe.service.ArticleService;
import com.kakao.cafe.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ArticleController {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final ArticleService articleService;
    private final UserService userService;

    public ArticleController(ArticleService articleService, UserService userService) {
        this.articleService = articleService;
        this.userService = userService;
    }

    @GetMapping("/questions/create")
    public String createForm(Model model) {
        usersAddAttribute(model);
        model.addAttribute("articleDto", new ArticleDto());
        return "qna/form";
    }

    @PostMapping("/questions")
    public String create(@Valid ArticleDto articleDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            usersAddAttribute(model);
            logger.error("errors={}", bindingResult);
            return "qna/form";
        }

        articleService.save(articleDto);
        return "redirect:/";
    }

    @GetMapping("/")
    public String showArticles(Model model) {
        List<Article> articles = articleService.findArticles();
        model.addAttribute("articles", articles);
        return "index";
    }

    @GetMapping("/articles/{articleId}")
    public String showArticle(@PathVariable int articleId, Model model) {
        Article article = articleService.findOne(articleId);
        String lineSeparator = System.lineSeparator();
        model.addAttribute("article", article);
        model.addAttribute("lineSeparator", lineSeparator);
        return "qna/show";
    }

    private void usersAddAttribute(Model model) {
        List<User> users = userService.findUsers();
        model.addAttribute("users", users);
    }

}
