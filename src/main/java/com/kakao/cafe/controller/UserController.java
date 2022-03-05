package com.kakao.cafe.controller;

import com.kakao.cafe.domain.UserInformation;
import com.kakao.cafe.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String getUserList(Model model) {
        logger.info("GET /users");
        model.addAttribute("users", userService.findAll());

        return "/user/list";
    }

    @GetMapping("/users/{userId}")
    public String getUserProfile(@PathVariable String userId, Model model) {
        logger.info("GET /users/{}", userId);
        model.addAttribute("user", userService.findOne(userId).get());

        return "/user/profile";
    }

    @GetMapping("/users/{id}/form")
    public String updateForm(@PathVariable String id, Model model) {
        logger.info("GET /users/{}/form", id);
        model.addAttribute("user", userService.findOne(id).get());

        return "/user/updateForm";
    }

    @PostMapping("/users")
    public String createUserInformation(UserInformation userInformation) {
        logger.info("POST /users userId = {} password = {} name = {} email = {}"
              , userInformation.getUserId(), userInformation.getPassword()
              , userInformation.getName(), userInformation.getEmail());
        userService.join(userInformation);

        return "redirect:/users";
    }

    @PutMapping("/users/{id}/update")
    public String updateUserInformation(@PathVariable String id, UserInformation updatedUserInformation) {
        logger.info("PUT /users userId = {} password = {} name = {} email = {}"
            , updatedUserInformation.getUserId(), updatedUserInformation.getPassword()
            , updatedUserInformation.getName(), updatedUserInformation.getEmail());
        userService.update(id, updatedUserInformation);

        return "redirect:/users";
    }
}
