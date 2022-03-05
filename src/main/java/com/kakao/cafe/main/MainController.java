package com.kakao.cafe.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.kakao.cafe.qna.domain.ArticleService;

@Controller
public class MainController {
	private final ArticleService articleService;
	private Logger logger = LoggerFactory.getLogger(MainController.class);

	public MainController(ArticleService articleService) {
		this.articleService = articleService;
	}

	@GetMapping()
	public String firstView(Model model) {
		model.addAttribute("articles", articleService.getAllArticles());
		return "index";
	}
}
