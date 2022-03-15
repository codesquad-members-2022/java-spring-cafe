package com.kakao.cafe.Controller;

import com.kakao.cafe.Controller.dto.ArticleForm;
import com.kakao.cafe.Controller.dto.ArticleResponse;
import com.kakao.cafe.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/qna")
    public String createForm(Model model) {
        model.addAttribute("articleDto", new ArticleForm());

        return "qna/form";
    }

    @PostMapping("/questions")
    public String create(@Valid ArticleForm articleForm, Errors errors) {
        if (errors.hasErrors()) {
            return "qna/form";
        }

        articleService.save(articleForm);

        return "redirect:/";
    }

    @GetMapping("/")
    public String articleList(Model model) {
        List<ArticleResponse> articles = articleService.findAll();
        model.addAttribute("articles", articles);

        return "index";
    }

    @GetMapping("/articles/{id}")
    public String findById(@PathVariable long id, Model model) {
        ArticleResponse article = articleService.findArticleResponseById(id);
        model.addAttribute("article", article);

        return "qna/show";
    }
}
