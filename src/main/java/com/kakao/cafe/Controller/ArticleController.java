package com.kakao.cafe.Controller;

import com.kakao.cafe.Controller.dto.ArticleDto;
import com.kakao.cafe.domain.Article;
import com.kakao.cafe.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ArticleController {

    private ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/qna")
    public String createForm(Model model) {
        model.addAttribute("articleDto", new ArticleDto());

        return "qna/form";
    }

    @PostMapping("/questions")
    public String create(@Valid ArticleDto articleDto, Errors errors) {
        if (errors.hasErrors()) {
            return "qna/form";
        }

        Article article = new Article(articleService.nextSequence(), articleDto);
        Long articleId = articleService.save(article);

        return "redirect:/";
    }

    @GetMapping("/")
    public String articleList(Model model) {
        List<Article> findArticles = articleService.findAll();

        List<ArticleDto> articles = findArticles.stream()
                .map(article -> new ArticleDto().toArticleDto(article))
                .collect(Collectors.toUnmodifiableList());


        model.addAttribute("articles", articles);

        return "index";
    }
}
