package com.kakao.cafe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.repository.ArticleRepository;

@Controller
public class ArticleController {

	private final ArticleRepository articleRepository;

	@Autowired
	public ArticleController(ArticleRepository articleRepository) {
		this.articleRepository = articleRepository;
	}

	@GetMapping("/article/new")
	public String createForm() {
		return "qna/form";
	}

	@PostMapping("/")
	public String create(String writer, String title, String contents) {
		Article article = new Article(writer, title, contents, articleRepository.calculateSize() + 1);
		articleRepository.save(article);
		return "redirect:/";
	}

	@GetMapping("/")
	public String articles(Model model) {
		List<Article> articles = articleRepository.findAll();
		model.addAttribute("articles", articles);
		return "index";
	}

	@GetMapping("/articles/{index}")
	public String article(@PathVariable String index, Model model) {
		model.addAttribute("article", articleRepository.findById(Integer.parseInt(index)));
		return "qna/show";
	}
}
