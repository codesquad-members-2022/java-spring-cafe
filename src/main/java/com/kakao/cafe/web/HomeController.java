package com.kakao.cafe.web;

import com.kakao.cafe.core.repository.MemberRepository;
import com.kakao.cafe.web.controller.article.dto.ArticleResponse;
import com.kakao.cafe.web.service.article.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;


@Controller
public class HomeController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ArticleService articleService;

    public HomeController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("")
    public String home(Model model) {
        List<ArticleResponse> articles = articleService.findAll()
                .stream()
                .map(ArticleResponse::new)
                .collect(Collectors.toList());
        model.addAttribute("articles", articles);
        return "article/list";
    }
}
