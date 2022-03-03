package com.kakao.cafe.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

	@GetMapping("/")
	public String welcome(Model model) {
		//todo
		// 모델에 값 넣어서 index에 전달하고
		// index html을 변경해야 한다.
		return "index";
	}

}
