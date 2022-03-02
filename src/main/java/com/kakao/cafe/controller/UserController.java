package com.kakao.cafe.controller;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.service.UserService;
import java.util.List;
import java.util.NoSuchElementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("users")
public class CafeController {

    Logger logger = LoggerFactory.getLogger(CafeController.class);
    UserService userService = new UserService();

    @PostMapping
    public String create(User form) {
        userService.signUp(new User(form));

        return "redirect:/users";
    }

    @GetMapping
    public String list(Model model) {
        List<User> users = userService.findUsers();
        model.addAttribute("users", users);
        return "users/list";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String userDetails(@PathVariable("id") Long id, Model model) {
        try {
            User user = userService.findOne(id);
            model.addAttribute("user", user);
        } catch (NoSuchElementException e) {
            //단건 조회 에러 예외처리 화면 리턴
        }
        return "users/profile";
    }

}
