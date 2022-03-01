package com.kakao.cafe.web.controller;

import com.kakao.cafe.web.domain.user.User;
import com.kakao.cafe.web.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/user/form")
	public String form() {
		return "user/form";
	}

//	@PostMapping("/user/create")
//	public String join(@RequestBody UserForm form) {
//		User user = new User();
//		user.setUserId(form.getUserId());
//		user.setPassword(form.getPassword());
//		user.setName(form.getName());
//		user.setEmail(form.getEmail());
//
//		userService.join(user);
//
//		return "redirect:/users";
//	}

	@PostMapping("/user/create")
	public String join(@RequestParam String userId,
		@RequestParam String password,
		@RequestParam String name,
		@RequestParam String email) {
		User user = new User();
		user.setUserId(userId);
		user.setPassword(password);
		user.setName(name);
		user.setEmail(email);

		userService.join(user);

		return "redirect:/users";
	}

	@GetMapping("/user/list")
	public String list(Model model) {
		List<User> users = userService.findAllUsers();
		model.addAttribute("users", users);
		return "user/list";
	}

	@GetMapping("/users")
	public String users(Model model) {
		List<User> users = userService.findAllUsers();
		model.addAttribute("users", users);
		return "/user/list";
	}

	@GetMapping("/user/profile")
	public String profile() {
		return "user/profile";
	}

}
