package com.kakao.cafe.web;

import com.kakao.cafe.service.ReplyService;
import com.kakao.cafe.web.dto.ReplyDto;
import com.kakao.cafe.web.dto.ReplyResponseDto;
import com.kakao.cafe.web.dto.ReplyUpdateDto;
import com.kakao.cafe.web.dto.SessionUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/qna/{articleId}/reply")
public class ReplyController {

    private final Logger logger = LoggerFactory.getLogger(ReplyController.class);

    private final ReplyService replyService;

    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    @PostMapping("/write")
    public String write(ReplyDto replyDto, HttpSession httpSession) {
        SessionUser sessionedUser = SessionUser.from(httpSession);
        String sessionedUserId = sessionedUser.getUserId();
        logger.info("[{}] write reply[{}] in [article{}]", sessionedUserId, replyDto.getContents(), replyDto.getArticleId());

        ReplyResponseDto replyResponseDto = replyService.write(sessionedUserId, replyDto);

        return "redirect:/qna/show/" + replyResponseDto.getArticleId();
    }

    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable Integer articleId, @PathVariable Integer id, HttpSession httpSession) {
        SessionUser sessionedUser = SessionUser.from(httpSession);
        replyService.delete(sessionedUser.getUserId(), id);
        logger.info("[{}] delete reply[{}] in [article{}]", sessionedUser.getUserId(), id, articleId);

        return "redirect:/qna/show/" + articleId;
    }

    @PostMapping("/{id}/update")
    public String update(ReplyUpdateDto replyUpdateDto, HttpSession httpSession) {
        SessionUser sessionedUser = SessionUser.from(httpSession);
        replyService.update(replyUpdateDto, sessionedUser.getUserId());

        return "redirect:/qna/show/" + replyUpdateDto.getArticleId();
    }
}
