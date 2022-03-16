package com.kakao.cafe.user.domain;

import static com.kakao.cafe.common.utils.session.SessionUser.*;
import static com.kakao.cafe.common.utils.session.SessionUtils.*;
import static com.kakao.cafe.main.MainController.*;
import static com.kakao.cafe.user.domain.UserUpdateDto.*;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kakao.cafe.common.utils.session.SessionUser;

@Controller
@RequestMapping("/users")
public class UserController {
	public static final String REDIRECT_USER_ROOT_URL = REDIRECT_ROOT + "users";
	Logger logger = LoggerFactory.getLogger(UserController.class);
	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping()
	public String signUp(UserDto.Request userDto) {
		userDto.isValid(logger);
		logger.info("user sign up : {}",userDto.getUserId());
		userService.register(userDto);
		return REDIRECT_USER_ROOT_URL;
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
		boolean isValid = isValidAccess(userId, httpSession, logger);
		if (!isValid) {
			return REDIRECT_LOGIN_VIEW;
		}
		logger.info("view for profile updating : {}", userId);
		UserUpdateDto.Response response = userService.findUserForUpdateFrom(userId);
		/*
			비밀번호 입력 제한시 수정 요청시에는 수정요청이 불가하다는 별도의 안내 메시지를 전달한다.
			비밀번호 입력 제한 10분이 지난 후에는 변경 요청이 가능하다.
		 */
		if (response.hasMessage()) {
			model.addAttribute("notAllow", response.getMessage());
		}
		model.addAttribute("user", response);
		return "/user/updateForm";
	}

	@PostMapping("/{user-id}/update")
	public String update(@PathVariable(value = "user-id") String userId, UserUpdateDto.Request userDto, RedirectAttributes redirectAttributes) {
		userDto.isValid(logger);
		/*
			비밀번호가 일치해야 이름, 이메일을 변경할 수 있다.
			비밀번호는 3회 이상 오류시 10분 동안 변경 요청 할 수 없다.
		 */
		if (!userService.isValidPassword(userDto)) {
			UserUpdateDto.WrongPasswordResponse invalidResponse = UserUpdateDto.WrongPasswordResponse.from(userDto);
			setTimeLimit(userId, redirectAttributes, invalidResponse);
			invalidResponse.addCount();
			redirectAttributes.addFlashAttribute("checks", invalidResponse);
			String redirectUrl = String.format("users/%s/form", userId);
			return REDIRECT_ROOT + redirectUrl;
		}

		logger.info("update profile: {}", userId);
		userService.changeProfile(userDto);
		return REDIRECT_USER_ROOT_URL;
	}

	// 403..
	private void setTimeLimit(String userId, RedirectAttributes redirectAttributes,
		WrongPasswordResponse passwordResponse) {
		if (!passwordResponse.isValidChangingPassword()) {
			userService.restrictPasswordChange(userId);
			redirectAttributes.addFlashAttribute("notAllow", USER_MESSAGE_OF_EXCEED_PASSWORD_ENTRY);
		}
	}

	/*
		에러메시지 반환과 에러화면으로 끝납니다.
	 */
	@ExceptionHandler(value = IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ModelAndView illegalArgumentException(HttpServletRequest request, IllegalArgumentException exception) {
		String requestURI = request.getRequestURI();
		ModelAndView mav = new ModelAndView(requestURI);
		mav.addObject("message", exception.getMessage());
		return mav;
	}
}
