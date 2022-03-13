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
import com.kakao.cafe.user.domain.UserService;

@Controller
public class MainController {
	private final ArticleService articleService;
	private final UserService userService;

	private Logger logger = LoggerFactory.getLogger(MainController.class);

	public MainController(ArticleService articleService, UserService userService) {
		this.articleService = articleService;
		this.userService = userService;
	}

	@GetMapping("/")
	public String firstView(Model model) {
		model.addAttribute("articles", articleService.getAllArticles());
		return "index";
	}

	@PostMapping("/do_login")
	public String login(LoginDto loginDto, HttpSession httpSession, RedirectAttributes redirectAttributes) {
		boolean isValidated = userService.validateLogin(loginDto, redirectAttributes);
		if (!isValidated) {
			return "redirect:/login";
		}

		logger.info("login : {}", loginDto.getUserId());
		httpSession.setAttribute(SESSION_KEY, from(loginDto.getUserId()));
		httpSession.setMaxInactiveInterval(1200);  // 20분
		return "redirect:/";
	}

	@GetMapping("/logout")
	public String logout(HttpSession httpSession) {
		httpSession.removeAttribute(SESSION_KEY);
		return "redirect:/";
	}
}
