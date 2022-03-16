package com.kakao.cafe.controller;

import com.kakao.cafe.dto.ArticleResponse;
import com.kakao.cafe.dto.ArticleSaveRequest;
import com.kakao.cafe.service.ArticleService;
import com.kakao.cafe.session.SessionUser;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    // article
    @GetMapping("/")
    public String listArticles(Model model) {
        List<ArticleResponse> articles = articleService.findArticles();
        model.addAttribute("articles", articles);
        return "qna/list";
    }

    @GetMapping("articles/{id}")
    public String showArticle(@PathVariable(value = "id") Integer articleId, Model model) {
        ArticleResponse article = articleService.findArticle(articleId);
        model.addAttribute("article", article);
        return "qna/show";
    }

    @GetMapping("/articles/form")
    public String formCreateArticle() {
        return "qna/form";
    }

    @PostMapping("/articles")
    public String createArticle(ArticleSaveRequest request, HttpSession session) {
        SessionUser user = SessionUser.from(session);
        articleService.write(user, request);
        return "redirect:/";
    }

    @GetMapping("articles/{id}/form")
    public String formUpdateArticle(@PathVariable(value = "id") Integer articleId, Model model,
        HttpSession session) {
        SessionUser user = SessionUser.from(session);
        ArticleResponse article = articleService.mapUserArticle(user, articleId);
        model.addAttribute("article", article);
        return "qna/form";
    }

    @PutMapping("articles/{id}")
    public String updateArticle(@PathVariable(value = "id") Integer articleId,
        ArticleSaveRequest request, HttpSession session) {
        SessionUser user = SessionUser.from(session);
        articleService.updateArticle(user, request, articleId);
        return "redirect:/";
    }

    @DeleteMapping("articles/{id}")
    public String deleteArticle(@PathVariable(value = "id") Integer articleId,
        HttpSession session) {
        SessionUser user = SessionUser.from(session);
        articleService.deleteArticle(user, articleId);
        return "redirect:/";
    }

}
