package com.kakao.cafe.controller;

import com.kakao.cafe.domain.UserInformation;
import com.kakao.cafe.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String getUserList(Model model) {
        logger.info("GET /users");
        model.addAttribute("users", userService.findAllUsers());

        return "/user/list";
    }

    @GetMapping("/users/{userId}")
    public String getUserProfile(@PathVariable String userId, Model model) {
        logger.info("GET /users/{}", userId);
        model.addAttribute("user", userService.findOneUser(userId).get());

        return "/user/profile";
    }

    @PostMapping("/users")
    public ModelAndView createUserInformation(UserInformation userInformation) {
        logger.info("POST /users userId = {} password = {} name = {} email = {}"
              , userInformation.getUserId(), userInformation.getPassword()
              , userInformation.getName(), userInformation.getEmail());

        ModelAndView modelAndView = new ModelAndView("redirect:/users");
        modelAndView.addObject("joinedUserId", userService.join(userInformation).getUserId());

        return modelAndView;
    }
}
