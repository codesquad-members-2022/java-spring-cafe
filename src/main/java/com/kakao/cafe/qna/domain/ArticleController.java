package com.kakao.cafe.qna.domain;

import static com.kakao.cafe.common.utils.MessageFormatter.*;
import static com.kakao.cafe.common.utils.session.SessionUtils.*;
import static com.kakao.cafe.main.MainController.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.kakao.cafe.reply.domain.ReplyService;

@Controller
@RequestMapping("/questions")
public class ArticleController {
	private final ArticleService articleService;
	private final ReplyService replyService;
	private Logger logger = LoggerFactory.getLogger(ArticleController.class);

	public ArticleController(ArticleService articleService, ReplyService replyService) {
		this.articleService = articleService;
		this.replyService = replyService;
	}

	@GetMapping()
	public String viewFroAsk(HttpSession httpSession) {
		boolean isValid = isValidLogin(httpSession, logger);
		if (!isValid) {
			return REDIRECT_LOGIN_VIEW;
		}
		return "/qna/form";
	}

	@PostMapping()
	public String ask(ArticleDto.WriteRequest articleDto) {
		articleDto.isValid(logger);
		logger.info("request question : {}", articleDto);
		articleService.write(articleDto);
		return REDIRECT_ROOT;
	}

	/*
		게시판의 게시글과 댓글을 별도로 각각 불러옵니다.
		DB join 해서 한번에 가져오는 걸 해봤지만,
		 댓글수 만큼 게시글 반복되어서 좋지 않았습니다.
		 DB를 한 번만 다녀오면서, 좀더 효율 적인 방법이 있을까요?
	 */
	@GetMapping("/{id}")
	public String detail(@PathVariable Long id, HttpSession httpSession, Model model) {
		boolean isValid = isValidLogin(httpSession, logger);
		if (!isValid) {
			return REDIRECT_LOGIN_VIEW;
		}
		logger.info("request details of question: {}", id);
		model.addAttribute("question", articleService.read(id));
		model.addAttribute("replies", replyService.getListOfReplyByArticle(id));
		return "qna/show";
	}

	@GetMapping("/{id}/form")
	public String sendViewAndEdit(@PathVariable Long id, HttpSession httpSession, Model model) {
		boolean isValid = isValidLogin(httpSession, logger);
		if (!isValid) {
			return REDIRECT_LOGIN_VIEW;
		}
		logger.info("request fixing question: {}", id);
		ArticleDto.WriteResponse question = articleService.getArticle(id, getHttpSessionAttribute(httpSession));
		model.addAttribute("question", question);
		return "qna/updateForm";
	}

	@PutMapping("/{id}")
	public String edit(@PathVariable Long id, ArticleDto.EditRequest updateDto, HttpSession httpSession) {
		logger.info("request edit question: {}", id);
		articleService.edit(updateDto, getHttpSessionAttribute(httpSession));
		String url = String.format("%squestions/%d", REDIRECT_ROOT, id);
		return url;
	}

	@DeleteMapping("/{id}")
	public String remove(@PathVariable Long id, HttpSession httpSession) {
		boolean isValid = isValidLogin(httpSession, logger);
		if (!isValid) {
			return REDIRECT_LOGIN_VIEW;
		}
		logger.info("request delete article: {}", id);
		articleService.remove(id, getHttpSessionAttribute(httpSession));
		return REDIRECT_ROOT;
	}

	@ExceptionHandler(value = IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ModelAndView illegalArgumentException(HttpServletRequest request, IllegalArgumentException exception) {
		String requestURI = request.getRequestURI();
		ModelAndView mav = new ModelAndView(requestURI);
		mav.addObject("message", toMessageLines(exception.getMessage()));
		return mav;
	}
}
