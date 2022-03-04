package com.kakao.cafe.web.controller;

import com.kakao.cafe.web.domain.user.User;
import com.kakao.cafe.web.domain.user.UserDto;
import com.kakao.cafe.web.service.UserService;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/user/create")
	public String join(UserForm form) {
		User user = new User(form.getUserId(), form.getPassword(), form.getName(), form.getEmail());
		userService.join(user);

		return "redirect:/users";
	}

	@GetMapping("/users")
	public String list(Model model) {
		List<UserDto> users = userService.findAllUsers().stream()
			.map(UserDto::new)
			.collect(Collectors.toList());
		model.addAttribute("users", users);
		return "user/list";
	}

	@GetMapping("/users/{userId}")
	public String getUserByUserId(@PathVariable String userId, Model model) {
		User originUser = userService.findByUserId(userId)
			.orElseThrow(() -> new NoSuchElementException("해당 userId를 가진 회원은 존재하지 않습니다."));
		UserDto user = new UserDto(originUser);
		model.addAttribute("user", user);
		return "user/profile";
	}

}
