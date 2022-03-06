package com.kakao.cafe.web;


import com.kakao.cafe.service.ArticleService;
import com.kakao.cafe.web.dto.ArticleDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {

    private final Logger logger = LoggerFactory.getLogger(ArticleController.class);

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/qna/write-qna")
    public String writeForm() {
        logger.info("User in qna-form");

        return "/qna/form";
    }

    @PostMapping("/qna/write-qna")
    public String write(ArticleDto articleDto) {
        logger.info("User writing...{}", articleDto);
        articleService.write(articleDto.toEntity());

        return "redirect:/qna/all";
    }

    @GetMapping("/qna/all")
    public String showAll(Model model) {
        logger.info("show articles");
        model.addAttribute("articles", articleService.findAll());

        return "/qna/list";
    }

    @GetMapping("/qna/show/{id}")
    public String showArticle(@PathVariable int id, Model model) {
        logger.info("show article{}",id);
        model.addAttribute("article", articleService.findOne(id));

        return "/qna/show";
    }
}
