package com.kakao.cafe.article.controller;

import com.kakao.cafe.article.controller.dto.ArticleWriteRequest;
import com.kakao.cafe.article.service.ArticleService;
import com.kakao.cafe.exception.domain.RequiredFieldNotFoundException;
import com.kakao.cafe.users.exception.UserUnsavedException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

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
}
