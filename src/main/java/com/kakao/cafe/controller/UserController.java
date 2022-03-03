package com.kakao.cafe.controller;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
        userService.join(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String userList(Model model) {
        List<User> userList = userService.findAllUser();
        model.addAttribute("userList", userList);
        return "user/list";
    }

    @GetMapping("/users/{userId}")
    public String userProfile(@PathVariable("userId") String userId, Model model) {
        User user = userService.findOne(userId);
        System.out.println(user);
        model.addAttribute(user);
        return "user/profile";
    }

}
