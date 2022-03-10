package com.kakao.cafe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.service.UserService;

@Controller
public class UserController {
    private final UserService userService;

    @Autowired //-> 생성자가 하나일 경우 생략 가능. 하지만 명시해주는게 더 좋은 것 같아 놔둠
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/list") // 회원 목록으로 이동
    public String list(Model model) {
        List<User> users = userService.findUsers();
        model.addAttribute("users", users);
        return "/user/list";
    }

    @GetMapping("/user/form") // 회원 가입 페이지로 이동
    public String form() {
        return "/user/form";
    }

    @PostMapping("/user") // 회원 가입
    public String create(User user) {
        userService.join(user);
        return "redirect:/user/list"; // redirect 역할 : 파일(list.html)이 아닌 URL(/user/list)을 호출한다
    }

    @GetMapping("/user/{nickname}") // 회원 정보 조회 페이지로 이동
    public String viewProfile(@PathVariable String nickname, Model model) {
        User user = userService.findByNickname(nickname);
        model.addAttribute("userProfile", user);
        return "/user/profile";
    }

    @GetMapping("/user/{nickname}/form") // 회원 정보 수정 페이지로 이동
    public String profileForm(@PathVariable String nickname, Model model) {
        User user = userService.findByNickname(nickname);
        model.addAttribute("user", user);
        return "/user/updateForm";
    }

    // @PutMapping("/user")
    // public String updateForm()
}

