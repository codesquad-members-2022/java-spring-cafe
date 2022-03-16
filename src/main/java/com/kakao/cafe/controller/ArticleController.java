package com.kakao.cafe.controller;

import com.kakao.cafe.domain.articles.Articles;
import com.kakao.cafe.domain.users.Users;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {

	@GetMapping("/articles")
	public String write() {
		return "qna/form";
	}

	@PostMapping(path = "users/signup")
	public String save(Articles articles) {


		return "redirect:/users/list";
	}
}
