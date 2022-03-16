package com.kakao.cafe.controller;

import com.kakao.cafe.domain.articles.Articles;
import com.kakao.cafe.domain.users.Users;
import com.kakao.cafe.service.articles.ArticlesService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {

	private ArticlesService articlesService;

	public ArticleController(ArticlesService articlesService) {
		this.articlesService = articlesService;
	}

	@GetMapping("/articles")
	public String write() {
		return "qna/form";
	}

	@PostMapping(path = "/articles")
	public String save(Articles articles) {
		articlesService.save(articles);
		return "redirect:/";
	}
}
