package com.kakao.cafe.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.kakao.cafe.domain.user.User;
import com.kakao.cafe.domain.user.dto.UserDto;
import com.kakao.cafe.domain.user.dto.UserProfileDto;
import com.kakao.cafe.service.UserService;

@Controller
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private static final String SESSION_NAME = "sessionUser";

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public String addUser(User user) {
        logger.info("POST /users");
        userService.save(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String showUsers(Model model, HttpSession session) {
        logger.info("GET /users");
        Object value = session.getAttribute(SESSION_NAME);
        if (value == null) {
            throw new IllegalArgumentException("[ERROR] 로그인 후 확인할 수 있습니다.");
        }
        List<UserDto> userDtoList = userService.findAllUser();
        model.addAttribute("users", userDtoList);
        return "list";
    }

    @GetMapping("/users/{userId}")
    public String showUserProfile(@PathVariable String userId, Model model) {
        logger.info("GET /users/{}", userId);
        UserProfileDto userProfileDto = userService.findUserProfileByUserId(userId);
        model.addAttribute("userProfile", userProfileDto);
        return "user/profile";
    }

    @GetMapping("/users/{userId}/check")
    public String verifyPassword(@PathVariable String userId, Model model, HttpSession session) {
        logger.info("GET /users/{}/check", userId);
        Object value = session.getAttribute(SESSION_NAME);
        if (value != null) {
            User user = (User)value;
            if (!user.isYourId(userId)) {
                throw new IllegalArgumentException("[ERROR] 자신의 계정만 수정할 수 있습니다.");
            }
        }
        model.addAttribute("userId", userId);
        return "user/passwordCheck";
    }

    @PostMapping("/users/{userId}/form")
    public String editMemberInformation(@PathVariable String userId, String password, Model model) {
        logger.info("POST /users/{}/form", userId);
        userService.checkPasswordMatch(userId, password);
        User user = userService.findUserByUserId(userId);
        return "user/updateForm";
    }

    @PutMapping("/users/{userId}/update")
    public String updateUserInformation(@PathVariable String userId, User updateUser) {
        logger.info("PUT /users/update");
        userService.update(userId, updateUser);
        return "redirect:/users";
    }

    @PostMapping("/user/login")
    public String login(String userId, String password, HttpSession session) {
        logger.info("POST /user/login");
        User user = userService.findUserByUserId(userId);
        if (user.isYourPassword(password)) {
            session.setAttribute(SESSION_NAME, user);
            return "redirect:/";
        }
        return "login/login_failed";
    }
}
