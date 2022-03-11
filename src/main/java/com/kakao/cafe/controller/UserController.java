package com.kakao.cafe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.kakao.cafe.Service.UserService;
import com.kakao.cafe.domain.User;

@Controller
public class UserController {

	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/user/join")
	public String createForm() {
		return "user/form";
	}

	@PostMapping("/users")
	public String create(String userId, String password, String name, String email) {
		User user = new User(userId, password, name, email);
		userService.join(user);
		return "redirect:/users";
	}

	@GetMapping("/users")
	public String users(Model model) {
		List<User> users = userService.findUsers();
		model.addAttribute("users", users);
		return "user/list";
	}

	@GetMapping("/user/login")
	public String login() {
		return "user/login";
	}

	@GetMapping("/users/{userId}")
	public String profile(@PathVariable String userId, Model model) {
		model.addAttribute("user", userService.findOne(userId).get());
		return "user/profile";
	}
}
