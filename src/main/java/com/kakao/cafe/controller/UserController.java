package com.kakao.cafe.controller;

import com.kakao.cafe.controller.dto.UsersDto;
import com.kakao.cafe.domain.users.Users;
import com.kakao.cafe.service.users.UserService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/users/signup")
	public String signup() {
		return "user/form";
	}

	@PostMapping(path = "users/signup")
	public String save(Users user) {
		userService.save(user);

		return "redirect:/users/list";
	}

	@GetMapping("/users/list")
	public String list(Model model) {
		List<UsersDto> userList = userService.list();
		model.addAttribute("userList", userList);
		return "user/list";
	}

	@GetMapping("/users/{userId}")
	public String profile(Model model, @RequestParam String userId) {
		Users user = userService.one(userId);
		model.addAttribute("user", user);
		return "user/profile";
	}
}
