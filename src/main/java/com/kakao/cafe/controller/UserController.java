package com.kakao.cafe.controller;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.UserRepository;
import com.kakao.cafe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.lang.model.SourceVersion;
import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/user/form")
    public String signUp() {
        return "user/form";
    }

    @PostMapping("/user/create")
    public String createUser(HttpServletRequest request) {
        User user = new User(request.getParameter("userId"),
                            request.getParameter("password"),
                            request.getParameter("name"),
                            request.getParameter("email"));
        userService.save(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String userList() {

        return "user/list";
    }

    @GetMapping("/users/profile")
    public String userProfile() {

        return "user/profile";
    }

}
