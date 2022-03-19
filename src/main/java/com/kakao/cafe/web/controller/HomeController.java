package com.kakao.cafe.web.controller;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.service.QuestionService;
import com.kakao.cafe.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class HomeController {
    private final QuestionService questionService;

    public HomeController(QuestionService questionService) {
        this.questionService = questionService;
    }

    // TODO : name 하드코딩 변경
    @GetMapping("/")
    public String home(@SessionAttribute(name="loginUser", required = false) User user, Model model) {
        model.addAttribute("questions", questionService.findQuestions());

        if (user == null) {
            return "index";
        }

        return "index_login";
    }
}
