package com.kakao.cafe.web.questions;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.service.ArticleService;
import com.kakao.cafe.web.questions.dto.ArticleDto;
import com.kakao.cafe.web.validation.ArticleValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/questions")
public class ArticleController {

    private final ArticleService articleService;
    private final ArticleValidation articleValidation;
    private final Logger log = LoggerFactory.getLogger(ArticleController.class);

    public ArticleController(ArticleService articleService, ArticleValidation articleValidation) {
        this.articleService = articleService;
        this.articleValidation = articleValidation;
    }

    @InitBinder
    public void init(WebDataBinder dataBinder) {
        dataBinder.addValidators(articleValidation);
    }

    @GetMapping
    public String requestForm(Model model) {
        log.info("create form 접근");
        model.addAttribute("article", new ArticleDto());
        return "/qna/form";
    }

    @PostMapping
    public String saveForm(@Validated @ModelAttribute("article") ArticleDto dto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.info("saveForm Validation had Errors");
            return "/qna/form";
        }

        articleService.addArticle(dto);
        log.info("save form = {}", dto.getTitle());
        return "redirect:/";
    }

    @GetMapping("/{index}")
    public String getArticle(@PathVariable Long index, Model model) {
        Article findArticle = articleService.findArticleById(index);
        model.addAttribute("article", findArticle);
        log.info("get article title = {}", findArticle.getTitle());
        log.info("get article content = {}", findArticle.getContents());
        return "/qna/show";
    }
}
