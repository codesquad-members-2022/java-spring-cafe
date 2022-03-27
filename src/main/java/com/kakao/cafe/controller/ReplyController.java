package com.kakao.cafe.controller;

import com.kakao.cafe.dto.ReplyResponse;
import com.kakao.cafe.dto.Result;
import com.kakao.cafe.service.ReplyService;
import com.kakao.cafe.session.SessionUser;
import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/articles")
public class ReplyController {

    private final ReplyService replyService;

    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    // reply
    @PostMapping("/{questionId}/answers")
    public ReplyResponse createAnswer(@PathVariable(value = "questionId") Integer articleId,
        String comment, HttpSession session) {
        SessionUser user = SessionUser.from(session);
        return replyService.comment(user, articleId, comment);
    }

    @DeleteMapping(value = "/{questionId}/answers/{id}")
    public Result deleteAnswer(@PathVariable(value = "id") Integer replyId, HttpSession session) {
        SessionUser user = SessionUser.from(session);
        replyService.deleteReply(user, replyId);
        return Result.ok();
    }

    @ExceptionHandler
    public Result handleException(Exception e) {
        return Result.error(e);
    }


}
