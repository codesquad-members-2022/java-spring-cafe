package com.kakao.cafe.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

	@GetMapping("/user/form")
	public String form() {
		return "user/form";
	}

	@GetMapping("/user/list")
	public String list() {
		return "user/list";
	}

	@GetMapping("/user/profile")
	public String profile() {
		return "user/profile";
	}

}
