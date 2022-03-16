package com.kakao.cafe.reply.domain;

import static com.kakao.cafe.common.utils.session.SessionUtils.*;
import static com.kakao.cafe.main.MainController.*;

import javax.servlet.http.HttpSession;

import org.apache.commons.text.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/questions/{questionId}/reply")
public class ReplyController {
	private final ReplyService replyService;

	private Logger logger = LoggerFactory.getLogger(ReplyController.class);

	public ReplyController(ReplyService replyService) {
		this.replyService = replyService;
	}

	@PostMapping()
	public String sendViewForCommentOnThePost(@PathVariable Long questionId, String content, HttpSession httpSession) {
		boolean isValid = isValidLogin(httpSession, logger);
		if (!isValid) {
			return REDIRECT_LOGIN_VIEW;
		}
		logger.info("comment view about article: {} - {}", questionId, content);
		replyService.leaveAComment(questionId,
									StringEscapeUtils.escapeHtml4(content),
									getHttpSessionAttribute(httpSession));
		String url = String.format("%squestions/%d", REDIRECT_ROOT, questionId);
		return url;
	}
}
