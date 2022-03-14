package com.kakao.cafe.article.controller;

import com.kakao.cafe.article.controller.dto.ArticleDetail;
import com.kakao.cafe.article.controller.dto.ArticleResponse;
import com.kakao.cafe.article.controller.dto.ArticleWriteRequest;
import com.kakao.cafe.article.domain.Article;
import com.kakao.cafe.article.service.ArticleService;
import com.kakao.cafe.exception.domain.RequiredFieldNotFoundException;
import com.kakao.cafe.users.exception.UserUnsavedException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("/questions")
    public ModelAndView write(ArticleWriteRequest writeRequest, ModelAndView modelAndView) {

        try {
            articleService.write(writeRequest);
            modelAndView.setViewName("redirect:/");
            return modelAndView;
        } catch (UserUnsavedException | RequiredFieldNotFoundException e) {
            modelAndView.setViewName("qna/form");
            modelAndView.setStatus(HttpStatus.BAD_REQUEST);
            return modelAndView;
        }
    }

    @GetMapping("/")
    public ModelAndView index(ModelAndView modelAndView) {
        List<Article> articles = articleService.findArticles();

        if (articles.size() > 0) {
            modelAndView.addObject("articles", toArticleResponseList(articles));
        }

        modelAndView.addObject("articleCount", articles.size());
        modelAndView.setViewName("index");

        return modelAndView;
    }

    @GetMapping("/articles/{id}")
    public ModelAndView viewArticle(@PathVariable("id") Long id, ModelAndView modelAndView) {
        Article article = articleService.viewArticle(id);

        modelAndView.setViewName("qna/show");
        modelAndView.addObject("article", ArticleDetail.of(article));

        return modelAndView;
    }

    private List<ArticleResponse> toArticleResponseList(List<Article> articles) {
        return articles.stream()
                .map(ArticleResponse::of)
                .collect(Collectors.toUnmodifiableList());
    }
}
