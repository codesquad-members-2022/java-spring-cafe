package com.kakao.cafe.reply.domain;

import static com.kakao.cafe.common.utils.MessageFormatter.*;
import static com.kakao.cafe.common.utils.StringValidator.*;
import static com.kakao.cafe.common.utils.session.SessionUtils.*;
import static com.kakao.cafe.main.MainController.*;
import static com.kakao.cafe.user.domain.UserDto.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.text.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

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
		isValidContent(content);
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

	private void isValidContent(String content) {
		if (isNullOrBlank(content)) {
			throw new IllegalArgumentException(ERROR_OF_WHITE_SPACE);
		}
	}

	@ExceptionHandler(value = IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ModelAndView illegalArgumentException(HttpServletRequest request, IllegalArgumentException exception) {
		logger.error("error of request reply : {}", exception);
		String requestURI = request.getRequestURI();
		ModelAndView mav = new ModelAndView(requestURI);
		mav.addObject("message", toMessageLines(exception.getMessage()));
		return mav;
	}
}
