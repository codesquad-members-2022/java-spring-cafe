package com.kakao.cafe.web.controller.article;

import com.kakao.cafe.web.controller.article.dto.ArticleResponse;
import com.kakao.cafe.web.controller.article.dto.ArticleWriteRequest;
import com.kakao.cafe.web.service.article.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("articles")
@Controller
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("write")
    public String write() {
        return "article/write";
    }

    @PostMapping("")
    public String write(ArticleWriteRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "list";
        }
        articleService.write(request.toEntity());
        return "redirect:/articles";
    }

    @GetMapping("")
    public String getArticles(Model model) {
        List<ArticleResponse> articles = articleService.findAll()
                .stream()
                .map(ArticleResponse::new)
                .collect(Collectors.toList());
        model.addAttribute("articles", articles);
        return "article/list";
    }

    @GetMapping("{id}")
    public String getArticleDetails(@PathVariable("id") Integer id, Model model) {
        ArticleResponse findArticle = new ArticleResponse(articleService.findById(id));
        model.addAttribute("findArticle", findArticle);
        return "article/detail";
    }
}