package com.kakao.cafe.user.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {
	Logger logger = LoggerFactory.getLogger(UserController.class);
	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping()
	public String signUp(UserDto userDto) {
		logger.info("user sign up {}",userDto);
		userService.register(userDto);
		return "redirect:/users";
	}

	@GetMapping()
	public String list(Model model) {
		logger.info("users list");
		model.addAttribute("users",userService.findUsers());
		return "/user/list";
	}

	@GetMapping("/{userId}")
	public String update(@PathVariable Long userId, Model model) {
		model.addAttribute("user",userService.findUser(userId));
		return "/user/profile";
	}
}
