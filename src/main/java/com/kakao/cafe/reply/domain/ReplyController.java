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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.kakao.cafe.common.utils.session.SessionUser;

@Controller
@RequestMapping("/questions/{question-id}/reply")
public class ReplyController {
	private final ReplyService replyService;

	private Logger logger = LoggerFactory.getLogger(ReplyController.class);

	public ReplyController(ReplyService replyService) {
		this.replyService = replyService;
	}

	@PostMapping()
	public String writeAComment(@PathVariable(value = "question-id") Long questionId,
								String content,
		 						HttpSession httpSession) {
		isValidContent(content);
		boolean isValid = isValidLogin(httpSession, logger);
		if (!isValid) {
			return REDIRECT_LOGIN_VIEW;
		}
		logger.info("comment view about article: {} - {}", questionId, content);
		replyService.leaveAComment(questionId,
									StringEscapeUtils.escapeHtml4(content),
									getHttpSessionAttribute(httpSession));
		return getUrlRedirectToQuestionDetails(questionId);
	}

	@DeleteMapping("/{reply-id}")
	public String deleteComment(@PathVariable(value = "question-id") Long questionId,
								@PathVariable(value = "reply-id") Long replyId,
								String replier,
								HttpSession httpSession) {
		boolean isValid = isValidLogin(httpSession, logger);
		if (!isValid) {
			return REDIRECT_LOGIN_VIEW;
		}
		isValidRequestAccessor(replier, httpSession);
		logger.info("request deletion of comment: {}", replyId);

		replyService.remove(replyId);
		return getUrlRedirectToQuestionDetails(questionId);
	}

	private void isValidRequestAccessor(String replier, HttpSession httpSession) {
		SessionUser loginInfo = (SessionUser)getHttpSessionAttribute(httpSession);
		if (!loginInfo.isEquals(replier)) {
			throw new IllegalArgumentException("invalid accessor for delete a comment: " + loginInfo.getUserId());
		}
	}

	private void isValidContent(String content) {
		if (isNullOrBlank(content)) {
			throw new IllegalArgumentException(ERROR_OF_WHITE_SPACE);
		}
	}

	private String getUrlRedirectToQuestionDetails(Long questionId) {
		return String.format("%squestions/%d", REDIRECT_ROOT, questionId);
	}

	@ExceptionHandler(value = IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ModelAndView illegalArgumentException(IllegalArgumentException exception) {
		logger.error("error of request reply : {}", exception);
		ModelAndView mav = new ModelAndView("common/error");
		mav.addObject("message", toMessageLines(exception.getMessage()));
		return mav;
	}
}
