package com.kakao.cafe.web.controller;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.service.QuestionService;
import com.kakao.cafe.service.UserService;
import com.kakao.cafe.web.session.SessionConst;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {
    private final QuestionService questionService;

    public HomeController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("questions", questionService.findQuestions());
        return "index";
    }
}
