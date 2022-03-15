package com.kakao.cafe.controller;

import com.kakao.cafe.dto.ArticleResponse;
import com.kakao.cafe.dto.ArticleSaveRequest;
import com.kakao.cafe.service.ArticleService;
import com.kakao.cafe.service.ReplyService;
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
    private final ReplyService replyService;

    public ArticleController(ArticleService articleService, ReplyService replyService) {
        this.articleService = articleService;
        this.replyService = replyService;
    }

    // question
    @GetMapping("/questions")
    public String formCreateQuestion() {
        return "qna/form";
    }

    @PostMapping("/questions")
    public String createQuestion(ArticleSaveRequest request, HttpSession session) {
        SessionUser user = SessionUser.from(session);
        articleService.write(user, request);
        return "redirect:/";
    }

    // article
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
        SessionUser user = SessionUser.from(session);
        ArticleResponse article = articleService.mapUserArticle(user, articleId);
        model.addAttribute("article", article);
        return "qna/form";
    }

    @PutMapping("articles/{id}")
    public String updateQuestion(@PathVariable(value = "id") Integer articleId,
        ArticleSaveRequest request, HttpSession session) {
        SessionUser user = SessionUser.from(session);
        articleService.updateArticle(user, request, articleId);
        return "redirect:/";
    }

    @DeleteMapping("articles/{id}")
    public String deleteQuestion(@PathVariable(value = "id") Integer articleId,
        HttpSession session) {
        SessionUser user = SessionUser.from(session);
        articleService.deleteArticle(user, articleId);
        return "redirect:/";
    }

    // reply
    @PostMapping("articles/{questionId}/answers")
    public String createAnswer(@PathVariable(value = "questionId") Integer articleId,
        String comment, HttpSession session) {
        SessionUser user = SessionUser.from(session);
        replyService.comment(user, articleId, comment);
        return String.format("redirect:/articles/%d/answers", articleId);
    }

}
