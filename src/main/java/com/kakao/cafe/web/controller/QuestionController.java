package com.kakao.cafe.web.controller;

import com.kakao.cafe.domain.Question;
import com.kakao.cafe.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/questions")
    public String qnaForm() {
        return "qna/form";
    }

    @PostMapping("/questions")
    public String createQuestion(@ModelAttribute Question question) {
        questionService.create(question);
        return "redirect:/";
    }

    @GetMapping("/")
    public String home(Model model) {
        List<Question> questions = questionService.findQuestions();
        model.addAttribute("questions", questions);
        return "index";
    }

    @GetMapping("/questions/{questionId}")
    public String question(@PathVariable Long questionId, Model model) {
        Question question = questionService.findOne(questionId);
        model.addAttribute("question", question);
        return "qna/show";
    }
}
