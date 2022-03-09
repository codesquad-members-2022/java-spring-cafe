package com.kakao.cafe.users.controller;

import com.kakao.cafe.users.controller.dto.UserJoinRequest;
import com.kakao.cafe.users.service.UserService;
import com.kakao.cafe.users.controller.dto.UserResponse;
import com.kakao.cafe.users.domain.User;
import com.kakao.cafe.users.exception.UserDuplicatedException;
import com.kakao.cafe.users.exception.UserUnsavedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ModelAndView join(UserJoinRequest joinRequest, ModelAndView model) {

        try {
            userService.join(joinRequest);
        } catch (UserUnsavedException | UserDuplicatedException e) {
            model.getModel().put("errorMessage", e.getMessage());
            model.setStatus(HttpStatus.BAD_REQUEST);
            model.setViewName("user/form");
            return model;
        }

        model.setViewName("redirect:/users");
        return model;
    }


    @GetMapping("/users")
    public ModelAndView userList(ModelAndView modelAndView) {
        List<User> users = userService.findUsers();
        modelAndView.addObject("users", toUserResponseDtoList(users));
        modelAndView.addObject("usersCount", users.size());
        modelAndView.setViewName("user/list");
        return modelAndView;
    }

    @GetMapping("/users/{id}")
    public ModelAndView findProfile(@PathVariable("id") Long id, ModelAndView modelAndView) {
        User user = userService.findOne(id);
        modelAndView.addObject("user", UserResponse.of(user));
        modelAndView.setViewName("user/profile");

        return modelAndView;
    }

    private List<UserResponse> toUserResponseDtoList(List<User> users) {
        return users.stream()
                .map(UserResponse::of)
                .collect(Collectors.toUnmodifiableList());
    }

}
