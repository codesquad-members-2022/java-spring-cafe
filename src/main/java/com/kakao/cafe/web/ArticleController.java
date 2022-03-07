package com.kakao.cafe.web;

import com.kakao.cafe.service.ArticleService;
import com.kakao.cafe.web.dto.ArticleRegisterFormDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {

    private static final Logger log = LoggerFactory.getLogger(ArticleController.class);
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("/questions")
    public String create(@ModelAttribute ArticleRegisterFormDto articleRegisterFormDto){
        log.info("---------[LOG] request to post question : {}", articleRegisterFormDto);
        articleService.register(articleRegisterFormDto);
        return "redirect:/";
    }

    @GetMapping("/")
    public String list(Model model) {
        log.info("---------[LOG] request to show question's list");
        System.out.println(articleService.showAll().size());
        model.addAttribute("questions",articleService.showAll());
        return "index";
    }
}
