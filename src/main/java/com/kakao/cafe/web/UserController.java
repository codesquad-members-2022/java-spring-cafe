package com.kakao.cafe.web;

import com.kakao.cafe.domain.user.User;
import com.kakao.cafe.service.UserService;
import com.kakao.cafe.web.dto.LoginDto;
import com.kakao.cafe.web.dto.UserJoinDto;
import com.kakao.cafe.web.dto.UserUpdateDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(IndexController.class);
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/form")
    public String joinForm() {
        log.info("회원가입 폼 불러오기");
        return "user/form";
    }

    @PostMapping("/form")
    public String join(UserJoinDto dto) {
        log.info("회원가입 하기");
        userService.userJoin(dto);
        return "redirect:/";
    }

    @GetMapping("/list")
    public String list(Model model) {
        log.info("회원 목록 확인하기");
        model.addAttribute("users", userService.findUsers());
        return "user/list";
    }

    @GetMapping("/{userId}")
    public String profile(@PathVariable String userId, Model model) {
        log.info("유저 아이디 = {} 로 회원정보 상세검색", userId);
        model.addAttribute("user", userService.findUser(userId));
        return "user/profile";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model) {
        log.info("유저번호 = {} 로 회원정보 수정하기 폼", id);
        model.addAttribute("user", userService.findById(id));
        return "user/updateForm";
    }

    @PutMapping("/{id}/form")
    public String update(@PathVariable Long id, UserUpdateDto userUpdateDto) {
        log.info("유저번호 = {} 로 회원정보 수정하기", id);
        userService.userUpdate(id, userUpdateDto);
        return "redirect:/user/list";
    }

    @GetMapping("/login")
    public String loginForm(LoginDto dto) {
        return "user/login";
    }

    @PostMapping("/login")
    public String login(LoginDto dto, HttpServletRequest request) {
        User user = userService.login(dto.getUserId(), dto.getPassword());
        if (user == null) {
            return "redirect:/user/login_failed";
        }
        HttpSession session = request.getSession();
        session.setAttribute("loginUser", user);

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }
}
