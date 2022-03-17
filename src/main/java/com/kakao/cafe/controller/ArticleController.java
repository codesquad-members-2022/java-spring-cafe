package com.kakao.cafe.controller;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.servcie.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/questions")
    public String qnaWriteForm(){
        return "post/form";
    }

    @PostMapping("/questions")
    public String qnaWriteRegister(@ModelAttribute Article article){
        articleService.qnaWrite(article);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String showWrite(@PathVariable int id, Model model){
        model.addAttribute("article", articleService.findById(id).get());
        return "post/show";
    }
}
