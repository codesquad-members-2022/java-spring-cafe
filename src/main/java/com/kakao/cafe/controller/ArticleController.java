package com.kakao.cafe.controller;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.dto.ArticleForm;
import com.kakao.cafe.service.ArticleService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/questions")
    public String createForm() {
        return "qna/form";
    }

    @PostMapping("/questions")
    public ModelAndView create(ArticleForm articleForm) throws Exception {
        Article article = articleService.write(articleForm);
        ModelAndView mav = new ModelAndView("redirect:/");
        mav.addObject("article", article);
        return mav;
    }

    @GetMapping("/")
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

}
