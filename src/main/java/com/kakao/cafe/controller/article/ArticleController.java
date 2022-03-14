package com.kakao.cafe.controller.article;

import com.kakao.cafe.domain.Article;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/articles")
public class ArticleController {

    @GetMapping("/new")
    public String createArticle() {
        return "articles/articleForm";
    }

    @PostMapping("/new")
    public String createArticleForm(ArticleForm form) {
        Article article = new Article(form.getTitle(), form.getText());
        return "redirect:/";
    }
}
