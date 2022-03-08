package com.kakao.cafe.users.controller;

import com.kakao.cafe.users.UserService;
import com.kakao.cafe.users.controller.dto.UserResponseDto;
import com.kakao.cafe.users.domain.User;
import com.kakao.cafe.users.exception.UserDuplicatedException;
import com.kakao.cafe.users.exception.UserNotFountException;
import com.kakao.cafe.users.exception.UserRuntimeException;
import com.kakao.cafe.users.exception.UserUnsavedException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
    public ModelAndView join(User user, ModelAndView model) {

        try {
            userService.join(user);
        } catch (UserUnsavedException | UserDuplicatedException e) {
            model.getModel().put("user", user);
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

        try {
            User user = userService.findOne(id);
            modelAndView.addObject(user);
            modelAndView.setViewName("user/profile");
        } catch (UserNotFountException e) {
            modelAndView.setViewName("user/list");
            modelAndView.setStatus(HttpStatus.BAD_REQUEST);
            modelAndView.addObject("errorMessage", "회원이 없습니다.");
        }

        return modelAndView;
    }

    @ExceptionHandler(UserRuntimeException.class)
    public String handleUserRuntimeException() {
        LoggerFactory.getLogger(getClass()).info("this is handleUserRuntimeException method");
        return "error/500";
    }

    private List<UserResponseDto> toUserResponseDtoList(List<User> users) {
        return users.stream()
                .map(UserResponseDto::of)
                .collect(Collectors.toUnmodifiableList());
    }

}
