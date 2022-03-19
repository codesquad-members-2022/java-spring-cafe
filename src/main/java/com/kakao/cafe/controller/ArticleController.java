package com.kakao.cafe.controller;

import com.kakao.cafe.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {

  private final ArticleService articleService;

  public ArticleController(ArticleService articleService) {
    this.articleService = articleService;
  }

  @GetMapping("/qna/new")
  public String questionForm() {
    return "qna/form";
  }

  @PostMapping("/qnaList")
  public String question(ArticleDTO form) {
    articleService.join(form);

    return "redirect:/";
  }
}
