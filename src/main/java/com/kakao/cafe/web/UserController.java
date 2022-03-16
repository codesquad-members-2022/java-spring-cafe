package com.kakao.cafe.web;

import com.kakao.cafe.CustomLogger;
import com.kakao.cafe.service.UserService;
import com.kakao.cafe.web.dto.UserLoginFormDto;
import com.kakao.cafe.web.dto.UserProfileDto;
import com.kakao.cafe.web.dto.UserRegisterFormDto;

import com.kakao.cafe.web.dto.UserUpdateFormDto;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

    private static final CustomLogger log = new CustomLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public String create(@ModelAttribute UserRegisterFormDto userRegisterFormDto) {
        log.info("sign up : {}", userRegisterFormDto);
        userService.register(userRegisterFormDto);
        return "redirect:/users";
    }

    @GetMapping
    public String list(Model model) {
        log.info("show user list");
        model.addAttribute("users", userService.showAll());
        return "/user/list";
    }

    @GetMapping("/{userId}")
    public String show(@PathVariable String userId, Model model) {
        log.info("show {}'s profile", userId);
        UserProfileDto userProfileDto = userService.showOne(userId);
        model.addAttribute("userId", userProfileDto.getUserId());
        model.addAttribute("email", userProfileDto.getEmail());
        return "/user/profile";
    }

    @GetMapping("/{userId}/form")
    public String updateForm(@PathVariable String userId, Model model, HttpSession httpSession) {
        log.info("show {}'s update form", userId);
        validateSession(httpSession, userId);
        model.addAttribute("userId", userId);
        return "/user/updateForm";
    }

    @PostMapping("/{userId}/update")
    public String update(@PathVariable String userId, UserUpdateFormDto userUpdateFormDto, HttpSession httpSession) {
        log.info("update {}'s profile : {}", userId, userUpdateFormDto);
        validateSession(httpSession, userId);
        userService.modify(userUpdateFormDto);
        return "redirect:/users";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute UserLoginFormDto userLoginFormDto,
        HttpSession httpSession) {
        log.info("user login attempt : {}", userLoginFormDto);
        httpSession.setAttribute("sessionedUserId", userService.login(userLoginFormDto));
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        log.info("user logout attempt");
        httpSession.invalidate();
        return "redirect:/";
    }

    private void validateSession(HttpSession httpSession, String userId) {
        String sessionedUserId = (String) httpSession.getAttribute("sessionedUserId");
        if (sessionedUserId == null) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }
        if (!sessionedUserId.equals(userId)) {
            throw new IllegalStateException("접근 권한이 없습니다.");
        }
    }
}
