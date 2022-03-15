package com.kakao.cafe.controller;

import com.kakao.cafe.dto.NewArticleParam;
import com.kakao.cafe.exception.article.ArticleDomainException;
import com.kakao.cafe.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/articles")
public class ArticleController {

    private static final Logger log = LoggerFactory.getLogger(ArticleController.class);

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("/write")
    public String writeArticle(NewArticleParam newArticleParam,
                               HttpServletRequest request) {

        System.out.println("hello");

        logRequestInfo(request);

        articleService.add(newArticleParam);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public ModelAndView getDetail(@PathVariable int id,
                                  HttpServletRequest request,
                                  ModelAndView mav) {

        logRequestInfo(request);

        mav.setViewName("qna/show");
        mav.addObject("article", articleService.search(id));
        return mav;
    }

    private void logRequestInfo(HttpServletRequest request) {
        Map<String, Object> params = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(name -> params.put(name, request.getParameter(name)));

        log.debug("{} {} {}", request.getMethod(), request.getRequestURI(), params);
    }

    @ExceptionHandler({ArticleDomainException.class})
    private ResponseEntity<String> except(ArticleDomainException ex) {
        return new ResponseEntity<>(ex.getMessage(), ex.getHttpStatus());
    }
}
