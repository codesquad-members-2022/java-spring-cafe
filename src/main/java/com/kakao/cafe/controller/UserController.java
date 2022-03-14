package com.kakao.cafe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.exception.ErrorMessage;
import com.kakao.cafe.repository.UserRepository;

@Controller
public class UserController {
	private final UserRepository userRepository;

	@Autowired
	public UserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@GetMapping("/user/new")
	public String createForm() {
		return "user/form";
	}

	@PostMapping("/users")
	public String create(String userId, String password, String name, String email) {
		User user = new User(userId, password, name, email);
		userRepository.save(user);
		return "redirect:/users";
	}

	@GetMapping("/users")
	public String users(Model model) {
		List<User> users = userRepository.findAll();
		model.addAttribute("users", users);
		return "user/list";
	}

	@GetMapping("/user/login")
	public String login() {
		return "user/login";
	}

	@GetMapping("/users/{userId}")
	public String profile(@PathVariable String userId, Model model) {
		User user = userRepository.findByUserId(userId)
			.orElseThrow(() -> new IllegalStateException(ErrorMessage.NO_SUCH_USER_ID.getMessage()));
		model.addAttribute("user", user);
		return "user/profile";
	}

	@GetMapping("/users/{userId}/form")
	public String updateForm(@PathVariable String userId, Model model) {
		User user = userRepository.findByUserId(userId)
			.orElseThrow(() -> new IllegalStateException(ErrorMessage.NO_SUCH_USER_ID.getMessage()));
		model.addAttribute("user", user);
		return "user/updateForm";
	}

	@PutMapping("/users/{userId}")
	public String updateProfile(@PathVariable String userId, String password, String name, String email, Model model) {
		User user = userRepository.findByUserId(userId)
			.orElseThrow(() -> new IllegalStateException(ErrorMessage.NO_SUCH_USER_ID.getMessage()));
		userRepository.delete(user);
		user.updateProfile(password, name, email);
		userRepository.save(user);
		model.addAttribute("user", user);
		return "redirect:/users";
	}
}
