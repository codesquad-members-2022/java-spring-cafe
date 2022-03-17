package com.kakao.cafe.web.questions;

import com.kakao.cafe.service.ReplyService;
import com.kakao.cafe.web.questions.dto.ReplyDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/questions")
public class ReplyController {

    ReplyService replyService;

    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    @PostMapping("/{questionId}/replies")
    public String createReply(@ModelAttribute ReplyDto dto, @PathVariable Long questionId) {
        replyService.addReply(dto, questionId);
        return "redirect:/questions/" + questionId;
    }

}
