package com.kakao.cafe.controller;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.exception.user.DuplicateUserIdException;
import com.kakao.cafe.exception.user.NoSuchUserException;
import com.kakao.cafe.exception.user.SaveUserException;
import com.kakao.cafe.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/join")
    public String signUp(User user, HttpServletRequest request) {
        logRequestInfo(request);

        userService.addUser(user);
        return "redirect:/users";
    }

    @GetMapping
    public ModelAndView getUserList(HttpServletRequest request,
                                    HttpServletResponse response,
                                    ModelAndView mav) {
        logRequestInfo(request);
        setResponseInfo(response);

        mav.setViewName("user/list");
        mav.addObject("users", userService.findAll());
        return mav;
    }

    @GetMapping("/{userId}")
    public ModelAndView getUserProfile(@PathVariable String userId,
                                       HttpServletRequest request,
                                       HttpServletResponse response,
                                       ModelAndView mav) {
        logRequestInfo(request);
        setResponseInfo(response);

        mav.setViewName("user/profile");
        mav.addObject("user", userService.findUser(userId));
        return mav;
    }

    private void logRequestInfo(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        Map<String, Object> params = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(name -> params.put(name, request.getParameter(name)));

        log.info("[{}] [{}] {}", method, requestURI, params);
    }

    private void setResponseInfo(HttpServletResponse response) {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
    }

    @ExceptionHandler({ DuplicateUserIdException.class, NoSuchUserException.class, SaveUserException.class })
    public ResponseEntity<String> except(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
