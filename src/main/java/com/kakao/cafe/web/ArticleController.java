package com.kakao.cafe.web;


import com.kakao.cafe.service.ArticleService;
import com.kakao.cafe.web.dto.ArticleDto;
import com.kakao.cafe.web.dto.ArticleResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/qna")
public class ArticleController {

    private final Logger logger = LoggerFactory.getLogger(ArticleController.class);

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/write-qna")
    public String writeForm() {
        logger.info("User in qna-form");

        return "/qna/form";
    }

    @PostMapping("/write-qna")
    public String write(ArticleDto articleDto) {
        logger.info("User writing qna{}", articleDto);
        articleService.write(articleDto.toEntity());

        return "redirect:/qna/all";
    }

    @GetMapping("/all")
    public String showAll(Model model) {
        logger.info("Show all articles");
        model.addAttribute("articles", articleService.findAll());

        return "/qna/list";
    }

    @GetMapping("/show/{id}")
    public String showArticle(@PathVariable int id, Model model) {
        logger.info("Search for articleId{} to show client", id);

        ArticleResponseDto result = articleService.findOne(id);
        model.addAttribute("article", result);
        logger.info("Show article{}", result);

        return "/qna/show";
    }
}
