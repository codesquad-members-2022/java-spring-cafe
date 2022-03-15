package com.kakao.cafe.controller.article;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/new")
    public String addArticleForm() {
        return "articles/articleForm";
    }

    @PostMapping("/new")
    public String saveArticleForm(ArticleForm form) {
        Article article = new Article(form.getId(), form.getTitle(), form.getText());
        articleService.join(article);
        return "redirect:/";
    }

    @GetMapping
    public String articles(Model model) {
        List<Article> articles = articleService.findArticles();
        model.addAttribute("articles", articles);
        return "home";
    }

    @GetMapping("/{id}")
    public String article(@PathVariable Integer id, Model model) {
        Article article = articleService.findArticle(id);
        model.addAttribute("article", article);
        return "/articles/article";
    }

    @PostConstruct
    public void init() {

        articleService.join(new Article(0, "코드스쿼드 문 열려면?", "example1"));
        articleService.join(new Article(1, "문지기가 필요합니다. 문지기 할 사람?", "example2"));
    }
}
