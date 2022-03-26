package com.kakao.cafe.web.questions;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.domain.Reply;
import com.kakao.cafe.domain.User;
import com.kakao.cafe.service.ArticleService;
import com.kakao.cafe.service.ReplyService;
import com.kakao.cafe.web.SessionConst;
import com.kakao.cafe.web.questions.dto.ArticleDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/questions")
public class ArticleController {

    private final ArticleService articleService;
    private final ReplyService replyService;
    private final Logger log = LoggerFactory.getLogger(ArticleController.class);

    public ArticleController(ArticleService articleService, ReplyService replyService) {
        this.articleService = articleService;
        this.replyService = replyService;
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

        User sessionedUser = (User) session.getAttribute(SessionConst.SESSIONED_USER);

        articleService.addArticle(dto, sessionedUser);
        log.info("save form = {}", dto.getTitle());
        return "redirect:/";
    }

    @GetMapping("/{index}")
    public String getArticle(@PathVariable Long index, Model model) {
        Article findArticle = articleService.findArticleById(index);
        List<Reply> replies = replyService.findAllReplyOnArticle(index);
        model.addAttribute("article", findArticle);
        model.addAttribute("replies", replies);
        log.info("get article title = {}, {}", findArticle.getTitle(), findArticle.getContents());
        return "/qna/show";
    }

    @PostMapping("/{index}/delete")
    public String deleteArticle(@PathVariable Long index, HttpSession session) {
        log.info("delete article id = {}", index);
        User sessionedUser = (User) session.getAttribute(SessionConst.SESSIONED_USER);
        articleService.deleteArticle(index, sessionedUser);
        return "redirect:/";
    }

    @GetMapping("/{index}/update")
    public String getArticleUpdateForm(@PathVariable Long index,
                                       HttpSession session, Model model) {
        log.info("get profile update form");
        User sessionedUser = (User) session.getAttribute(SessionConst.SESSIONED_USER);
        model.addAttribute("article", articleService.findArticleDtoById(index, sessionedUser));
        return "/qna/updateForm";
    }

    @PostMapping("/{index}/update")
    public String saveArticleUpdateForm(@Validated @ModelAttribute("article") ArticleDto dto,
                                        @PathVariable Long index, HttpSession session) {
        articleService.updateArticle(dto, index, (User) session.getAttribute(SessionConst.SESSIONED_USER));
        log.info("save form = {}", dto.getTitle());
        return "redirect:/questions/" + index;
    }
}
