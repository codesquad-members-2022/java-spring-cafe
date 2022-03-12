package com.kakao.cafe.user.domain;

import static com.kakao.cafe.main.MainController.*;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

import com.kakao.cafe.main.SessionUser;

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
		userDto.isValid(logger);
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

	@GetMapping("/{user-id}/form")
	public String updateView(@PathVariable(value = "user-id") String userId, Model model, HttpSession httpSession) {
		isValidAccess(userId, httpSession);

		logger.info("view for profile updating : {}", userId);
		UserDto.Response user = userService.findUserId(userId);
		model.addAttribute("user", user);
		return "/user/updateForm";
	}

	@PostMapping("/{user-id}/update")
	public String update(@PathVariable(value = "user-id") String userId, UserUpdateDto.Request userDto) {
		userDto.isValid(logger);
		logger.info("update profile: {}", userId);
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

	private void isValidAccess(String userId, HttpSession httpSession) {
		isNotEmptyAccessor(httpSession);
		isCompareLoginsWithUser(userId, httpSession);
	}

	private void isCompareLoginsWithUser(String userId, HttpSession httpSession) {
		SessionUser sessionUser = (SessionUser)httpSession.getAttribute(SESSIONED_ID);
		if (sessionUser.isDifferentFrom(userId)) {
			logger.error("invalid access by different identity : {}", sessionUser.getUserName());
			throw new IllegalArgumentException("로그인 정보를 입력 하세요.");
		}
	}

	private void isNotEmptyAccessor(HttpSession httpSession) {
		Object accessor = httpSession.getAttribute(SESSIONED_ID);
		if (Objects.isNull(accessor)) {
			logger.error("invalid access to personal information modification");
			throw new IllegalArgumentException("로그인 하세요");
		}
	}
}
