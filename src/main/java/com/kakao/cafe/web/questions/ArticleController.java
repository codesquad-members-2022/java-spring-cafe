package com.kakao.cafe.web.questions;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.service.ArticleService;
import com.kakao.cafe.web.questions.dto.ArticleDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/questions")
public class ArticleController {

    private final ArticleService articleService;
    private final Logger log = LoggerFactory.getLogger(ArticleController.class);


    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public String requestForm() {
        log.info("create form 접근");
        return "/qna/form";
    }

    @PostMapping
    public String saveForm(@ModelAttribute ArticleDto dto) {
        Article article = new Article(dto.getWriter(), dto.getTitle(), dto.getContents(), LocalDateTime.now());
        articleService.addArticle(article);
        log.info("save form = {}", article.getTitle());
        return "redirect:/";
    }

    @GetMapping("/{index}")
    public String getArticle(@PathVariable Long index, Model model){
        Article findArticle = articleService.findArticleById(index);
        model.addAttribute("article", findArticle);
        log.info("get article title = {}", findArticle.getTitle());
        log.info("get article content = {}", findArticle.getContents());
        return "/qna/show";
    }
}
