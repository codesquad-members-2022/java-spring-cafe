package com.kakao.cafe.web.controller.article;

import com.kakao.cafe.core.domain.article.Article;
import com.kakao.cafe.web.controller.article.dto.PostWriteRequest;
import com.kakao.cafe.web.controller.member.dto.ProfileChangeRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

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

    @PostMapping("write")
    public String write(PostWriteRequest request, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "article/list";
        }
        articleService.write(request.toEntity());
        return "article/write";
    }

    @GetMapping("")
    public String getArticles(Model model) {
        List<Article> articles = articleService.findAll();
        model.addAttribute("articles", articles);
        return "article/list";
    }

    @GetMapping("{id}")
    public String getArticleDetails(@PathVariable("id") int id, Model model) {
        Article findArticle = articleService.findById(id);
        model.addAttribute("findArticle", findArticle);
        return "article/detail";
    }

    @PostMapping("{id}/edit")
    public String edit(PostEditRequest request) {
        articleService.edit(request);
        return "redirect:/members";
    }
}