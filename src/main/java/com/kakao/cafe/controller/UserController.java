package com.kakao.cafe.controller;

import com.kakao.cafe.dto.ModifyProfileRequest;
import com.kakao.cafe.dto.SignUpRequest;
import com.kakao.cafe.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public String signUp(SignUpRequest SignUpRequest, HttpServletRequest request) {
        logRequestInfo(request);

        userService.add(SignUpRequest.convertToUser());
        return "redirect:/users";
    }

    @GetMapping
    public ModelAndView getUsers(HttpServletRequest request,
                                 ModelAndView mav) {

        logRequestInfo(request);

        mav.setViewName("user/list");
        mav.addObject("users", userService.searchAll());
        return mav;
    }

    @GetMapping("/{userId}")
    public ModelAndView getUserProfile(@PathVariable String userId,
                                       HttpServletRequest request,
                                       ModelAndView mav) {

        logRequestInfo(request);

        mav.setViewName("user/profile");
        mav.addObject("user", userService.search(userId));
        return mav;
    }

    @GetMapping("/{userId}/form")
    public ModelAndView goUpdateForm(@PathVariable String userId,
                                       HttpServletRequest request,
                                       ModelAndView mav) {

        logRequestInfo(request);

        mav.setViewName("user/updateForm");
        mav.addObject("user", userService.search(userId));
        return mav;
    }

    @PutMapping("/{userId}/update")
    public String modifyProfile(ModifyProfileRequest modifyProfileRequest,
                                HttpServletRequest request) {

        modifyProfileRequest.isValidRequest();
        logRequestInfo(request);

        userService.update(modifyProfileRequest.convertToUser());
        return "redirect:/users";
    }

    private void logRequestInfo(HttpServletRequest request) {
        Map<String, Object> params = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(name -> params.put(name, request.getParameter(name)));

        log.debug("{} {} {}", request.getMethod(), request.getRequestURI(), params);
    }

    @ExceptionHandler({
            IllegalArgumentException.class,
            NoSuchElementException.class,
            IllegalStateException.class })

    private ResponseEntity<String> except(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
