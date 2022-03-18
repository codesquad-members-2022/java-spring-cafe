package com.kakao.cafe.web.questions.dto;

import com.kakao.cafe.domain.Reply;
import com.kakao.cafe.domain.User;
import com.kakao.cafe.service.ReplyService;
import com.kakao.cafe.web.SessionConst;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/questions/{questionId}/replies")
public class ApiReplyController {

    private final ReplyService replyService;

    public ApiReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    @PostMapping
    public Reply createReply(@RequestParam String contents, @PathVariable Long questionId, HttpSession session) {
        User user = (User) session.getAttribute(SessionConst.SESSIONED_USER);
        return replyService.addReply(contents, questionId, user);
    }

    @DeleteMapping("/{id}")
    public String deleteReply(@PathVariable Long questionId, @PathVariable Long id, HttpSession session) {
        User sessionedUser = (User) session.getAttribute(SessionConst.SESSIONED_USER);
        replyService.deleteReply(id, sessionedUser); // 댓글 번호와 로그인 사용자 넘기기
        return "redirect:/questions/" + questionId;
    }
}
