package com.kakao.cafe.web.controller;

import com.kakao.cafe.web.domain.user.User;
import com.kakao.cafe.web.service.LoginService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class LoginController {

	private final LoginService loginService;

	public LoginController(LoginService loginService) {
		this.loginService = loginService;
	}

	@GetMapping("/login")
	public String loginForm() {
		return "user/login";
	}

	@PostMapping("/login")
	public String login(LoginForm form, HttpSession session) {
		User loginUser = loginService.login(form.getUserId(), form.getPassword());

		if (loginUser == null) {
			return "redirect:/user/login_failed";
		}

		session.setAttribute("loginUser", loginUser);

		return "redirect:/";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		if (session != null) {
			session.invalidate();
		}
		return "redirect:/";
	}

}
