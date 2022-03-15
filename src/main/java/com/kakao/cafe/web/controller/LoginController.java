package com.kakao.cafe.web.controller;

import com.kakao.cafe.web.domain.user.User;
import com.kakao.cafe.web.service.LoginService;
import javax.servlet.http.HttpSession;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user/login")
public class LoginController {

	private final Log log = LogFactory.getLog(getClass());

	private final LoginService loginService;

	public LoginController(LoginService loginService) {
		this.loginService = loginService;
	}

	@GetMapping
	public String loginForm() {
		return "user/login";
	}

	@PostMapping
	public String login(@RequestParam String userId, @RequestParam String password, HttpSession session) {
		log.info(userId);
		log.info(password);
		log.info(session);

		User loginUser = loginService.login(userId, password);

		if (loginUser == null) {
			log.info("잘못된 로그인 요청");
			return "redirect:/user/login";
		}

		log.info("로그인 성공");
		session.setAttribute("loginUser", loginUser);

		log.info(session.getAttribute("loginUser").toString());

		return "redirect:/";
	}

}
