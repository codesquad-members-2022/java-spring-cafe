package com.kakao.cafe.controller;

import java.util.List;

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

    // @Autowired  -> 생성자가 하나일 경우 생략 가능
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String list(Model model) {
        List<User> users = userService.findUsers();
        model.addAttribute("users", users);
        return "user/list";
    }

    @PostMapping("/users")
    public String create(User user) {
        userService.join(user);
        return "redirect:/users"; // redirect 역할 : 파일(users.html)이 아닌 URL(/users)을 호출한다
    }

    @GetMapping("/users/{nickname}")
    public String profile(@PathVariable String nickname, Model model) {
        User user = userService.findByNickname(nickname);
        model.addAttribute("userProfile", user);

        return "user/profile";
    }
}

