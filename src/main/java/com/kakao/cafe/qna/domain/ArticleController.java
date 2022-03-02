package com.kakao.cafe.qna.domain;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
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
	public String view() {
		return "/qna/form";
	}

	@PostMapping()
	public String askQuestion(ArticleDto articleDto) {
		logger.info("request question : {}", articleDto);
		articleService.write(articleDto);
		return "redirect:/";
	}

	@ExceptionHandler(value = IllegalArgumentException.class)
	public ModelAndView illegalArgumentException(HttpServletRequest request, IllegalArgumentException exception) {
		String requestURI = request.getRequestURI();
		ModelAndView mav = new ModelAndView(requestURI);
		mav.addObject("message", toMessageLines(exception.getMessage()));
		return mav;
	}

	private String[] toMessageLines(String message) {
		if (message.contains(",")) {
			return message.split(",");
		}
		return new String[] {message};
	}
}
