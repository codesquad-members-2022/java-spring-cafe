package com.kakao.cafe.Controller;

import com.kakao.cafe.Controller.dto.ArticleForm;
import com.kakao.cafe.Controller.dto.ArticleResponse;
import com.kakao.cafe.domain.Article;
import com.kakao.cafe.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        model.addAttribute("articleDto", new ArticleForm());

        return "qna/form";
    }

    @PostMapping("/questions")
    public String create(@Valid ArticleForm articleDto, Errors errors) {
        if (errors.hasErrors()) {
            return "qna/form";
        }

        Article article = new Article(articleService.nextSequence(), articleDto);
        articleService.save(article);

        return "redirect:/";
    }

    @GetMapping("/")
    public String articleList(Model model) {
        List<Article> findArticles = articleService.findAll();

        List<ArticleResponse> articles = findArticles.stream()
                .map(article -> new ArticleResponse().toArticleResponse(article))
                .collect(Collectors.toUnmodifiableList());

        model.addAttribute("articles", articles);

        return "index";
    }

    @GetMapping("/articles/{id}")
    public String findById(@PathVariable long id, Model model) {
        Article findArticle = articleService.findById(id);
        ArticleResponse article = new ArticleResponse().toArticleResponse(findArticle);

        model.addAttribute("article", article);

        return "qna/show";
    }
}
