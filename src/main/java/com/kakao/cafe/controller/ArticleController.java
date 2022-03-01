package com.kakao.cafe.controller;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.service.ArticleService;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/questions")
    public String createQuestion() {
        return "qna/form";
    }

    @PostMapping("/questions")
    public ModelAndView create(@RequestParam String writer,
        @RequestParam String title,
        @RequestParam String contents) {

        Article article = new Article(writer, title, contents);
        Article savedArticle = articleService.write(article);

        ModelAndView mav = new ModelAndView("redirect:/");
        mav.addObject("article", savedArticle);
        return mav;
    }

    @GetMapping(value = {"/", "articles"})
    public String list(Model model) {
        List<Article> articles = articleService.findArticles();
        model.addAttribute("articles", articles);
        return "qna/list";
    }

    @GetMapping("articles/{id}")
    public String show(@PathVariable(value = "id") Integer articleId, Model model) {
        Article article = articleService.findArticle(articleId);
        model.addAttribute("article", article);
        return "qna/show";
    }

    @PostConstruct
    public void setUp() {
        articleService.write(new Article("rxdcxdrnine", "GitHub", "difficult rebase"));
        articleService.write(new Article("Miller", "Code Squad", "spring cafe"));
    }

}
