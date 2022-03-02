package com.kakao.cafe.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	private Logger logger = LoggerFactory.getLogger(MainController.class);

	@GetMapping("/")
	public String hello() {
		return "index";
	}

}
