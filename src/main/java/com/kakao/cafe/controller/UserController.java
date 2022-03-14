package com.kakao.cafe.controller;

import com.kakao.cafe.dto.UserResponse;
import com.kakao.cafe.dto.UserSaveRequest;
import com.kakao.cafe.service.UserService;
import com.kakao.cafe.util.SessionUtil;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String listUsers(Model model) {
        List<UserResponse> users = userService.findUsers();
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/{userId}")
    public String showUser(@PathVariable String userId, Model model) {
        UserResponse user = userService.findUser(userId);
        model.addAttribute("user", user);
        return "user/profile";
    }

    @GetMapping("/form")
    public String formCreateUser() {
        return "user/form";
    }

    @PostMapping
    public String createUser(UserSaveRequest request, HttpSession session) {
        UserResponse user = userService.register(request);
        session.setAttribute(SessionUtil.SESSION_USER, user);
        return "redirect:/users";
    }

    @GetMapping("/{userId}/form")
    public String formUpdateUser(@PathVariable String userId, Model model, HttpSession session) {
        SessionUtil.checkUser(session, userId);

        UserResponse user = userService.findUser(userId);
        model.addAttribute("user", user);
        return "user/update_form";
    }

    @PutMapping("/{userId}")
    public String updateUser(@PathVariable String userId, UserSaveRequest request,
        HttpSession session) {
        UserResponse userResponse = SessionUtil.checkUser(session, userId);
        userService.updateUser(userResponse, request);
        return "redirect:/users";
    }

}
