package com.kakao.cafe.web.controller;

import com.kakao.cafe.web.domain.article.Article;
import com.kakao.cafe.web.service.ArticleService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {

	private final ArticleService articleService;

	public ArticleController(ArticleService articleService) {
		this.articleService = articleService;
	}

	@GetMapping("/")
	public String welcome(Model model) {
		List<Article> articles = articleService.findAllArticle();
		model.addAttribute("articles", articles);
		return "index";
	}

	@PostMapping("/questions")
	public String createArticle(Article article) {
		articleService.save(article);

		return "redirect:/";
	}

	@GetMapping("/articles/{id}")
	public String getUserByUserId(@PathVariable Long id, Model model) {
		Article article = articleService.findById(id)
			.orElseThrow(() -> new IllegalStateException("해당 게시글은 존재하지 않습니다."));
		model.addAttribute("article", article);
		return "qna/show";
	}

}
