package com.kakao.cafe.controller;

import com.kakao.cafe.controller.dto.ArticleSaveRequestDto;
import com.kakao.cafe.controller.dto.ArticleUpdateRequestDto;
import com.kakao.cafe.domain.article.Article;
import com.kakao.cafe.domain.user.User;
import com.kakao.cafe.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import javax.servlet.http.HttpSession;

@Controller
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/qna")
    public String articlePage() {
        return "qna/form";
    }

    @PostMapping("/questions")
    public String save(ArticleSaveRequestDto requestDto) {
        articleService.save(requestDto);
        return "redirect:/";
    }

    @GetMapping("/articles/{id}")
    public String articleDetail(@PathVariable Long id, Model model) {
        Article article = articleService.findById(id);
        model.addAttribute("article", article);
        return "qna/show";
    }

    @GetMapping("/articles/{id}/update")
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
        Article article = articleService.findById(id);
        validateSessionUser(article.getWriter(), session);
        model.addAttribute("article", article);
        return "qna/update_form";
    }

    @PutMapping("/articles/{id}/update")
    public String update(@PathVariable Long id, ArticleUpdateRequestDto dto, HttpSession session) {
        Article article = articleService.findById(id);
        validateSessionUser(article.getWriter(), session);
        articleService.update(dto);
        return "redirect:/articles/" + id;
    }

    private void validateSessionUser(String userId, HttpSession session) {
        Object sessionUser = session.getAttribute("sessionUser");
        if (sessionUser == null || !((User) sessionUser).getUserId().equals(userId)) {
            throw new IllegalArgumentException("본인이 작성한 글만 수정 가능합니다");
        }
    }
}
