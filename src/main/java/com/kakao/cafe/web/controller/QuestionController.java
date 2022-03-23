package com.kakao.cafe.web.controller;

import com.kakao.cafe.service.QuestionService;
import com.kakao.cafe.web.dto.question.QuestionDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/questions/new")
    public String showQuestionRegisterForm() {
        return "qna/form";
    }

    @PostMapping("/questions")
    public String createQuestion(@ModelAttribute QuestionDto questionDto) {
        questionService.create(questionDto);
        return "redirect:/";
    }

    @GetMapping("/questions/{questionId}")
    public String showQuestionContent(@PathVariable Long questionId, Model model) {
        model.addAttribute("question", questionService.findOne(questionId));
        return "qna/show";
    }
}
