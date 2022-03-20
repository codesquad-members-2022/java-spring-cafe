package com.kakao.cafe.controller;

import com.kakao.cafe.domain.articles.Articles;
import com.kakao.cafe.service.articles.ArticlesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

	@PostMapping("/articles")
	public String save(Articles articles) {
		articlesService.save(articles);
		return "redirect:/";
	}

	@GetMapping("/")
	public String list(Model model) {
		model.addAttribute("articlesList", articlesService.list());
		return "index";
	}

	@GetMapping("/articles/{articleId}")
	public String detail(@PathVariable long articleId, Model model) {
		Articles article = articlesService.findByArticleId(articleId);
		model.addAttribute("article", article);
		return "qna/show";
	}
}
