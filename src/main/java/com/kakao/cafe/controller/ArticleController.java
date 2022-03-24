package com.kakao.cafe.controller;

import com.kakao.cafe.dto.ArticleRequestDto;
import com.kakao.cafe.dto.ArticleResponseDto;
import com.kakao.cafe.dto.UserResponseDto;
import com.kakao.cafe.service.ArticleService;
import com.kakao.cafe.service.AuthService;
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
    private final AuthService authService;

    public ArticleController(ArticleService articleService, AuthService authService) {
        this.articleService = articleService;
        this.authService = authService;
    }

    @PostMapping("/questions")
    public String createArticle(ArticleRequestDto articleRequestDto) {
        articleService.upload(articleRequestDto);

        return "redirect:/";
    }

    @GetMapping("/articles/{id}")
    public String getArticle(@PathVariable int id, Model model) {
        model.addAttribute("article", articleService.findOne(id));

        return "qna/show";
    }

    @GetMapping("/questions/{id}/form")
    public String getArticleUpdatePage(@PathVariable int id, Model model, HttpSession httpSession) {
        UserResponseDto sessionOfUser = (UserResponseDto) httpSession.getAttribute("sessionUser");
        ArticleResponseDto articleResponseDto = articleService.findOne(id);
        authService.validateUserIdOfSession(articleResponseDto.getUserId(), sessionOfUser);
        model.addAttribute("article", articleResponseDto);

        return "qna/updateForm";
    }

    @PutMapping("/questions/{id}/update")
    public String updateArticle(@PathVariable int id, ArticleRequestDto articleRequestDto, HttpSession httpSession) {
        UserResponseDto sessionOfUser = (UserResponseDto) httpSession.getAttribute("sessionUser");
        String userId = articleRequestDto.getUserId();
        authService.validateUserIdOfSession(userId, sessionOfUser);
        articleService.update(id, articleRequestDto);

        return "redirect:/articles/" + id;
    }

    @DeleteMapping("/questions/{id}")
    public String deleteArticle(@PathVariable int id, HttpSession httpSession) {
        UserResponseDto sessionOfUser = (UserResponseDto) httpSession.getAttribute("sessionUser");
        ArticleResponseDto articleResponseDto = articleService.findOne(id);
        authService.validateUserIdOfSession(articleResponseDto.getUserId(), sessionOfUser);
        articleService.delete(id);

        return "redirect:/";
    }
}
