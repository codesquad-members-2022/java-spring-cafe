package com.kakao.cafe.qna.domain;

import static com.kakao.cafe.common.utils.MessageFormatter.*;
import static com.kakao.cafe.common.utils.session.SessionUtils.*;
import static com.kakao.cafe.main.MainController.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/questions")
public class ArticleController {
	private final ArticleService articleService;
	private Logger logger = LoggerFactory.getLogger(ArticleController.class);

	public ArticleController(ArticleService articleService) {
		this.articleService = articleService;
	}

	@GetMapping()
	public String view(HttpSession httpSession) {
		boolean isValid = isValidLogin(httpSession, logger);
		if (!isValid) {
			return REDIRECT_LOGIN_VIEW;
		}
		return "/qna/form";
	}

	@PostMapping()
	public String askQuestion(ArticleDto.WriteRequest articleDto) {
		articleDto.isValid(logger);
		logger.info("request question : {}", articleDto);
		articleService.write(articleDto);
		return REDIRECT_ROOT;
	}

	@GetMapping("/{id}")
	public String lookAtTheDetailsOfTheQuestion(@PathVariable Long id, Model model) {
		logger.info("request details of question: {}", id);
		ArticleDto.WriteResponse question = articleService.read(id);
		model.addAttribute("question", question);
		return "qna/show";
	}

	@ExceptionHandler(value = IllegalArgumentException.class)
	public ModelAndView illegalArgumentException(HttpServletRequest request, IllegalArgumentException exception) {
		String requestURI = request.getRequestURI();
		ModelAndView mav = new ModelAndView(requestURI);
		mav.addObject("message", toMessageLines(exception.getMessage()));
		return mav;
	}
}
