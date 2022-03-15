package com.kakao.cafe.controller;

import com.kakao.cafe.domain.user.User;
import com.kakao.cafe.domain.user.UserDto;
import com.kakao.cafe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String searchUserList(Model model) {
        List<User> users = userService.findAllUsers();
        model.addAttribute("users", users);

        return "user/list";
    }

    @GetMapping("/{userId}")
    public String profile(@PathVariable("userId") String userId, Model model) {
        User user = userService.findSingleUser(userId);
        model.addAttribute("user", user);

        return "user/profile";
    }

    @PostMapping("/join")
    public String create(UserDto userDto) {
        userService.createUser(userDto);
        return "redirect:/users";
    }

    @ExceptionHandler(IllegalStateException.class)
    public String createFailed(IllegalStateException e, Model model) {
        model.addAttribute("error", e.getMessage());
        return "user/form";
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String searchFailed(NoSuchElementException e, Model model) {
        model.addAttribute("error", e.getMessage());
        return "error/notfound";
    }
}
