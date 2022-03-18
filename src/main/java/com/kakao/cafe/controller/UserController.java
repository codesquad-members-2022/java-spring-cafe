package com.kakao.cafe.controller;

import com.kakao.cafe.controller.dto.UserJoinRequestDto;
import com.kakao.cafe.controller.dto.UserUpdateRequestDto;
import com.kakao.cafe.domain.user.User;
import com.kakao.cafe.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String userList(Model model) {
        List<User> users = userService.findUsers();
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/users/join")
    public String joinPage() {
        return "user/form";
    }

    @PostMapping("/users/create")
    public String join(UserJoinRequestDto requestDto) {
        log.info("UserJoinRequestDto: {}", requestDto);
        userService.join(requestDto);
        return "redirect:/users";
    }

    @GetMapping("/users/{userId}")
    public String profile(@PathVariable String userId, Model model) {
        User user = userService.findByUserId(userId);
        model.addAttribute("user", user);
        return "user/profile";
    }

    @GetMapping("/users/{userId}/form")
    public String updatePage(@PathVariable String userId, Model model, HttpServletRequest request) {
        User user = userService.findByUserId(userId);
        validateSessionUser(userId, request);
        model.addAttribute("user", user);
        return "user/updateForm";
    }

    @PostMapping("/users/{userId}/update")
    public String update(@PathVariable String userId, @ModelAttribute UserUpdateRequestDto dto, HttpServletRequest request) {
        validateSessionUser(userId, request);
        userService.update(dto);
        return "redirect:/users";
    }

    private void validateSessionUser(String userId, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object sessionUser = session.getAttribute("sessionUser");
        if (sessionUser == null || !((User) sessionUser).getUserId().equals(userId)) {
            throw new IllegalArgumentException("현재 로그인된 사용자만 수정 가능합니다");
        }
    }
}
