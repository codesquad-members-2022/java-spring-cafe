package com.kakao.cafe.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.kakao.cafe.domain.article.Article;
import com.kakao.cafe.domain.user.User;
import com.kakao.cafe.service.ArticleService;

@Controller
public class ArticleController {

    private final Logger log = LoggerFactory.getLogger(ArticleController.class);

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/")
    public String showAllArticle(Model model) {
        log.info("GET /");
        model.addAttribute("articles", articleService.showAllArticles());
        return "index";
    }

    @PostMapping("/questions")
    public String addQuestion(Article article) {
        log.info("POST /questions");
        articleService.save(article);
        return "redirect:/";
    }

    @GetMapping("/articles/{index}")
    public String showArticle(@PathVariable int index, Model model) {
        log.info("GET /articles/{}", index);
        model.addAttribute("article", articleService.showArticle(index));
        return "qna/showQna";
    }

    @GetMapping("/articles/{index}/edit")
    public String editArticle(@PathVariable int index, HttpServletRequest request, Model model) {
        log.info("GET /articles/{}/edit", index);
        Article article = validateSessionUser(index, request);
        model.addAttribute("article", article);
        return "qna/editQna";
    }

    @PutMapping("/articles/{index}/edit")
    public String updateArticle(@PathVariable Long index, Article updateArticle, HttpServletRequest request) {
        log.info("POST /articles/{}/edit", index);
        checkSessionUser(request.getSession());
        articleService.update(index, updateArticle);
        return "redirect:/";
    }

    @DeleteMapping("/articles/{index}")
    public String deleteArticle() {
        // TODO

        return null;
    }

    private Article validateSessionUser(int index, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = checkSessionUser(session);
        Article article = articleService.showArticle(index);
        if (!article.isYourWriter(user.getUserId())) {
            throw new IllegalArgumentException("[ERROR] 작성한 사용자만 수정할 수 있습니다.");
        }
        return article;
    }

    private User checkSessionUser(HttpSession session) {
        Object value = session.getAttribute("sessionUser");
        if (value == null) {
            throw new IllegalArgumentException("[ERROR] 로그인이 필요합니다.");
        }
        return (User) value;
    }
}
