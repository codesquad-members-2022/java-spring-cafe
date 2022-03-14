package com.kakao.cafe.controller;

import com.kakao.cafe.dto.ArticleResponse;
import com.kakao.cafe.dto.ArticleSaveRequest;
import com.kakao.cafe.dto.UserResponse;
import com.kakao.cafe.service.ArticleService;
import com.kakao.cafe.util.SessionUtil;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @GetMapping("/questions")
    public String formCreateQuestion() {
        return "qna/form";
    }

    @PostMapping("/questions")
    public String createQuestion(ArticleSaveRequest request, HttpSession session) {
        UserResponse user = SessionUtil.getUser(session);
        articleService.write(user, request);
        return "redirect:/";
    }

    @GetMapping("/")
    public String listQuestions(Model model) {
        List<ArticleResponse> articles = articleService.findArticles();
        model.addAttribute("articles", articles);
        return "qna/list";
    }

    @GetMapping("articles/{id}")
    public String showQuestion(@PathVariable(value = "id") Integer articleId, Model model) {
        ArticleResponse article = articleService.findArticle(articleId);
        model.addAttribute("article", article);
        return "qna/show";
    }

    @GetMapping("articles/{id}/form")
    public String formUpdateQuestion(@PathVariable(value = "id") Integer articleId, Model model,
        HttpSession session) {
        UserResponse user = SessionUtil.getUser(session);
        ArticleResponse article = articleService.findUserArticle(user, articleId);
        model.addAttribute("article", article);
        return "qna/form";
    }

    @PutMapping("articles/{id}")
    public String updateQuestion(@PathVariable(value = "id") Integer articleId,
        ArticleSaveRequest request, HttpSession session) {
        UserResponse user = SessionUtil.getUser(session);
        articleService.updateArticle(user, request, articleId);
        return "redirect:/";
    }

}
