package com.kakao.cafe.web.controller;

import com.kakao.cafe.web.domain.user.User;
import com.kakao.cafe.web.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/user/create")
	public String join(UserForm form) {
		User user = new User();
		user.setUserId(form.getUserId());
		user.setPassword(form.getPassword());
		user.setName(form.getName());
		user.setEmail(form.getEmail());

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
	public String users() {
		return "redirect:/user/list";
	}

	@GetMapping("/users/{userId}")
	public String getUserByUserId(@PathVariable String userId, Model model) {
		User user = userService.findByUserId(userId).get();
		model.addAttribute("user", user);
		return "user/profile";
	}

}
