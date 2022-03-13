package com.kakao.cafe.controller;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String viewList(Model model) {
        List<User> userList = this.userService.getAllUsers();
        model.addAttribute("userList", userList);
        return "../templates/list";
    }

    @GetMapping("/create")
    public String createForm() {
        return "/user/form";
    }

    @PostMapping("/create")
    public String create(UserForm form) {
        User user = new User(form);
        userService.join(user);
        return "redirect:/users";
    }

    @GetMapping("/{userId}")
    public String viewUserInfo(@PathVariable("userId") String userId, Model model) {
        User selectedUser;
        Optional<User> foundUser = userService.findById(userId);
        if (foundUser.isPresent()) {
            selectedUser = foundUser.get();
            model.addAttribute("user", selectedUser);
            return "../templates/profile";
        }
        return null; // 잘못된 접근 페이지 리다이렉트
    }
}
