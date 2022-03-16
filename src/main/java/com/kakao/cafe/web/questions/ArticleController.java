package com.kakao.cafe.web.questions;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.domain.User;
import com.kakao.cafe.service.ArticleService;
import com.kakao.cafe.web.questions.dto.ArticleDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions")
public class ArticleController {

    private final ArticleService articleService;
    private final Logger log = LoggerFactory.getLogger(ArticleController.class);

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public String requestForm(Model model) {
        log.info("create form 접근");
        model.addAttribute("article", new ArticleDto());
        return "/qna/form";
    }

    @PostMapping
    public String saveForm(@Validated @ModelAttribute("article") ArticleDto dto, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            log.info("saveForm Validation had Errors");
            return "/qna/form";
        }

        User sessioned_user = (User) session.getAttribute("SESSIONED_USER");

        articleService.addArticle(dto, sessioned_user);
        log.info("save form = {}", dto.getTitle());
        return "redirect:/";
    }

    @GetMapping("/{index}")
    public String getArticle(@PathVariable Long index, Model model) {
        Article findArticle = articleService.findArticleById(index);
        model.addAttribute("article", findArticle);
        log.info("get article title = {}", findArticle.getTitle());
        log.info("get article content = {}", findArticle.getContents());
        return "/qna/show";
    }

    @PostMapping("/{index}/delete")
    public String deleteArticle(@PathVariable Long index, HttpSession session) throws AuthenticationException {
        log.info("delete article id = {}", index);
        User sessioned_user = (User) session.getAttribute("SESSIONED_USER");
        articleService.deleteArticle(index, sessioned_user);
        return "redirect:/";
    }

    @GetMapping("/{index}/update")
    public String getArticleUpdateForm(@PathVariable Long index,
                                       HttpSession session, Model model) throws AuthenticationException {
        log.info("get profile update form");
        User sessioned_user = (User) session.getAttribute("SESSIONED_USER");
        model.addAttribute("article", articleService.findArticleDtoById(index, sessioned_user));
        return "/qna/updateForm";
    }

    @PostMapping("/{index}/update")
    public String saveArticleUpdateForm(@Validated @ModelAttribute("article") ArticleDto dto,
                                        @PathVariable Long index, HttpSession session) {
        articleService.updateArticle(dto, index, (User) session.getAttribute("SESSIONED_USER"));
        log.info("save form = {}", dto.getTitle());
        return "redirect:/questions/" + index;
    }
}
