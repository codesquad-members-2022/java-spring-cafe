package com.kakao.cafe.main;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.kakao.cafe.qna.domain.ArticleService;
import com.kakao.cafe.user.domain.User;
import com.kakao.cafe.user.domain.UserRepository;

@Controller
public class MainController {
	private final ArticleService articleService;
	private final UserRepository userRepository;

	private Logger logger = LoggerFactory.getLogger(MainController.class);

	public MainController(ArticleService articleService, UserRepository userRepository) {
		this.articleService = articleService;
		this.userRepository = userRepository;
	}

	@GetMapping()
	public String firstView(Model model) {
		model.addAttribute("articles", articleService.getAllArticles());
		return "index";
	}

	@PostMapping("/do_login")
	public String login(String userId, String password, HttpSession httpSession) {
		Optional<User> user = userRepository.findByUserId(userId);
		if (user.isEmpty()) {
			return "redirect:/login";
		}
		if (user.get().isDifferent(password)) {
			return "redirect:/login";
		}
		httpSession.setAttribute("user", user.get().getUserId());  // todo 암호화 -_-
		return "redirect:/";
	}
}
