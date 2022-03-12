package com.kakao.cafe.main;

import static com.kakao.cafe.main.SessionUser.*;
import static com.kakao.cafe.user.domain.UserUpdateDto.*;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

	@GetMapping("/")
	public String firstView(Model model) {
		model.addAttribute("articles", articleService.getAllArticles());
		return "index";
	}

	@PostMapping("/do_login")
	public String login(String userId, String password, HttpSession httpSession, RedirectAttributes redirectAttributes) {
		Optional<User> getUser = userRepository.findByUserId(userId);
		if (getUser.isEmpty()) {
			logger.info("login empty");
			return "redirect:/login";
		}
		User user = getUser.get();
		if (!user.isAllowedStatusOfPasswordEntry()) {
			redirectAttributes.addFlashAttribute("notAllow", USER_MESSAGE_OF_EXCEED_PASSWORD_ENTRY);
			return "redirect:/login";
		}
		if (user.isDifferentPassword(password)) {
			logger.info("login different password : {}", password);
			return "redirect:/login";
		}
		logger.info("login : {}", userId);
		httpSession.setAttribute(SESSION_KEY, from(userId));
		httpSession.setMaxInactiveInterval(600);  // 10분
		return "redirect:/";
	}

	@GetMapping("/logout")
	public String logout(HttpSession httpSession) {
		httpSession.removeAttribute(SESSION_KEY);
		return "redirect:/";
	}
}
