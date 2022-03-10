package com.kakao.cafe.controller;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping
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
    public String writeArticle(ModelAndView modelAndView) {
        modelAndView.setViewName("qna/form");
        return "qna/form";
    }

    @GetMapping("/")
    public String mainPage(Model model) {
        List<Article> articleList = articleService.findAllArticle();
        model.addAttribute(articleList);
        return "index";
    }

    @GetMapping("article/{articleId}")
    public String showArticle(@PathVariable("articleId") int articleId, Model model) {
        Article article = articleService.findOneArticle(articleId);
        model.addAttribute(article);
        return "qna/show";
    }

}
