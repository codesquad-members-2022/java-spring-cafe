package com.kakao.cafe.controller;

import com.kakao.cafe.controller.dto.UserLoginRequestDto;
import com.kakao.cafe.domain.user.User;
import com.kakao.cafe.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.NoSuchElementException;

@Controller
public class LoginController {
    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/login")
    public String loginPage() {
        return "/user/login";
    }

    @PostMapping("/users/login")
    public String login(@ModelAttribute UserLoginRequestDto dto, HttpServletRequest req) {
        HttpSession session = req.getSession();
        User loginUser = userService.login(dto);
        session.setAttribute("sessionUser", loginUser);
        return "redirect:/";
    }

    @ExceptionHandler({NoSuchElementException.class, IllegalArgumentException.class})
    public String loginFail(HttpServletRequest req, Model model) {
        String userId = req.getParameter("userId");
        model.addAttribute("userId", userId);
        return "user/login_failed";
    }
}
