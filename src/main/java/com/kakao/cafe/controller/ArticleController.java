package com.kakao.cafe.controller;

import com.kakao.cafe.controller.dto.ArticleSaveDto;
import com.kakao.cafe.domain.User;
import com.kakao.cafe.service.ArticleService;
import com.kakao.cafe.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ArticleController {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final ArticleService articleService;
    private final UserService userService;

    public ArticleController(ArticleService articleService, UserService userService) {
        this.articleService = articleService;
        this.userService = userService;
    }

    @GetMapping("/questions/create")
    public String createForm(Model model) {
        addAttributeUsers(model);
        model.addAttribute("articleSaveDto", new ArticleSaveDto());
        return "qna/form";
    }

    @PostMapping("/questions")
    public String create(@Valid ArticleSaveDto articleSaveDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            addAttributeUsers(model);
            logger.error("errors={}", bindingResult);
            return "qna/form";
        }

        articleService.save(articleSaveDto);
        return "redirect:/";
    }

    private void addAttributeUsers(Model model) {
        List<User> users = userService.findUsers();
        model.addAttribute("users", users);
    }

}
