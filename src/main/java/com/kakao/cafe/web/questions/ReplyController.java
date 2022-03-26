package com.kakao.cafe.web.questions;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.service.ReplyService;
import com.kakao.cafe.web.SessionConst;
import com.kakao.cafe.web.questions.dto.ReplyDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions/{questionId}/replies")
public class ReplyController {

    private final ReplyService replyService;

    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    @PostMapping
    public String createReply(@ModelAttribute ReplyDto dto, @PathVariable Long questionId) {
        replyService.addReply(dto, questionId);
        return "redirect:/questions/" + questionId;
    }

    @DeleteMapping("/{id}")
    public String deleteReply(@PathVariable Long questionId, @PathVariable Long id, HttpSession session) {
        User sessionedUser = (User) session.getAttribute(SessionConst.SESSIONED_USER);
        replyService.deleteReply(id, sessionedUser); // 댓글 번호와 로그인 사용자 넘기기
        return "redirect:/questions/" + questionId;
    }
}
