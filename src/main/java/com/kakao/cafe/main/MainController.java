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
	public static final String SESSIONED_ID = "sessionedUser";
	private final ArticleService articleService;
	private final UserRepository userRepository;

	private Logger logger = LoggerFactory.getLogger(MainController.class);

	public MainController(ArticleService articleService, UserRepository userRepository) {
		this.articleService = articleService;
		this.userRepository = userRepository;
	}

	@GetMapping("/")
	public String firstView(Model model) {
		model.addAttribute("articles", articleService.getAllArticles());
		return "index";
	}

	@PostMapping("/do_login")
	public String login(String userId, String password, HttpSession httpSession) {
		logger.info("login user : {}", userId);
		Optional<User> getUser = userRepository.findByUserId(userId);
		if (getUser.isEmpty()) {
			return "redirect:/login";
		}
		User user = getUser.get();
		if (user.isDifferent(password)) {
			return "redirect:/login";
		}
		SessionUser sessionUser = new SessionUser(String.valueOf(user.getId()), userId);
		httpSession.setAttribute(SESSIONED_ID, sessionUser);
		return "redirect:/";
	}

	@GetMapping("/logout")
	public String logout(HttpSession httpSession) {
		httpSession.removeAttribute(SESSIONED_ID);
		return "redirect:/";
	}
}
