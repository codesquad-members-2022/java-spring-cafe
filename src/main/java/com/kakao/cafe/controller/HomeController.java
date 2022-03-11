package com.kakao.cafe.controller;

import com.kakao.cafe.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/")
public class HomeController {

    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    private final ArticleService articleService;

    public HomeController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public ModelAndView getArticles(ModelAndView mav,
                                       HttpServletRequest request) {

        logRequestInfo(request);

        mav.setViewName("qna/list");
        mav.addObject("articles", articleService.searchAll());
        return mav;
    }

    private void logRequestInfo(HttpServletRequest request) {
        Map<String, Object> params = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(name -> params.put(name, request.getParameter(name)));

        log.debug("{} {} {}", request.getMethod(), request.getRequestURI(), params);
    }
}
