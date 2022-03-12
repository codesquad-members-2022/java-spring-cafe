package com.kakao.cafe.controller;

import com.kakao.cafe.controller.dto.UserDto;
import com.kakao.cafe.domain.User;
import com.kakao.cafe.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RequestMapping("/users")
@Controller
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "user/form";
    }

    @PostMapping
    public String create(@Valid UserDto userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logger.error("errors={}", bindingResult);
            return "user/form";
        }
        User user = userDto.toEntity();
        userService.join(user);
        return "redirect:/users";
    }

    @GetMapping
    public String showUsers(Model model) {
        List<User> users = userService.findUsers();
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/{userId}")
    public String showUserProfile(@PathVariable String userId, Model model) {
        User user = userService.findOne(userId);
        model.addAttribute("user", user);
        return "user/profile";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ModelAndView returnFormAndSendExceptionMessage(HttpServletRequest request, HttpServletResponse response, IllegalArgumentException exception) throws IOException {
        logger.error("IllegalArgumentException={}", exception.getMessage());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("exception", exception.getMessage());

        String requestURI = request.getRequestURI();
        if (requestURI.equals("/users")) {
            UserDto userDto = changeParamsToUserDto(request);
            modelAndView.addObject(userDto);
            modelAndView.setViewName("user/form");
        }

        if (!modelAndView.hasView()) {
            response.sendError(404, "페이지를 찾을 수 없습니다");
        }
        return modelAndView;
    }

    private UserDto changeParamsToUserDto(HttpServletRequest request) {
        UserDto userDto = new UserDto();
        userDto.setUserId(request.getParameter("userId"));
        userDto.setPassword(request.getParameter("password"));
        userDto.setName(request.getParameter("name"));
        userDto.setEmail(request.getParameter("email"));
        return userDto;
    }
}
