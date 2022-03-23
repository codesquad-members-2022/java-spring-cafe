package com.kakao.cafe.controller;

import com.kakao.cafe.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

  @GetMapping("/articles/{id}")
  public String showArticle(@PathVariable Integer id, Model model) {
    ArticleDTO articleDTO = articleService.findOneByIndex(id);
    model.addAttribute("article", articleDTO);
    return "qna/show";
  }
}
