package com.kakao.cafe.user.domain;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
	public String signUp(UserDto.Request userDto) {
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

	@GetMapping("/{id}")
	public String readProfile(@PathVariable Long id, Model model) {
		logger.info("profile : {}", id);
		UserDto.Response user = userService.find(id);
		model.addAttribute("user", user);
		return "/user/profile";
	}

	@GetMapping("/{id}/form")
	public String updateView(@PathVariable Long id, Model model) {
		logger.info("view profile: {}", id);
		UserDto.Response user = userService.find(id);
		model.addAttribute("user", user);
		return "/user/updateForm";
	}

	@PostMapping("/{id}/update")
	public String update(@PathVariable Long id, UserUpdateDto.Request userDto, Model model) {
		userDto.isValid(logger);
		logger.info("update profile: {}", id);
		userService.changeProfile(userDto);
		return "redirect:/users/";
	}

	/*
		에러메시지 반환과 에러화면으로 끝납니다.
	 */
	@ExceptionHandler(value = IllegalArgumentException.class)
	public ModelAndView illegalArgumentException(HttpServletRequest request, IllegalArgumentException exception) {
		String requestURI = request.getRequestURI();
		ModelAndView mav = new ModelAndView(requestURI);
		mav.addObject("message", exception.getMessage());
		return mav;
	}
}
