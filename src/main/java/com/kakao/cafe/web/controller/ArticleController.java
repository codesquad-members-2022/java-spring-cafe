package com.kakao.cafe.web.controller;

import com.kakao.cafe.web.domain.article.Article;
import com.kakao.cafe.web.service.ArticleService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ArticleController {

	private final ArticleService articleService;

	public ArticleController(ArticleService articleService) {
		this.articleService = articleService;
	}

	@PostMapping("/questions")
	public String createArticle(
		@RequestParam String writer,
		@RequestParam String title,
		@RequestParam String contents) {
		Article article = new Article(writer, title, contents);

		articleService.save(article);

		return "redirect:/";
	}

	@GetMapping("/")
	public String welcome(Model model) {
		List<Article> articles = articleService.findAllArticle();
		model.addAttribute("articles", articles);
		return "index";
	}

}
