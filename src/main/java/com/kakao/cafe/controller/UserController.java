package com.kakao.cafe.controller;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.dto.ModifiedUserParam;
import com.kakao.cafe.dto.NewUserParam;
import com.kakao.cafe.exception.user.UserDomainException;
import com.kakao.cafe.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

    @PostMapping("/register")
    public String signUp(NewUserParam newUserParam,
                         HttpServletRequest request) {

        logRequestInfo(request);

        userService.add(newUserParam);
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
                                     HttpSession session,
                                     ModelAndView mav) {

        logRequestInfo(request);

        User user = (User) session.getAttribute("userInfo");
        mav.setViewName("user/updateForm");
        mav.addObject("user", user);

        if (!user.ownerOf(userId)) {
            mav.setViewName("error/4xx");
            mav.addObject("message", "본인 정보만 수정할 수 있습니다.");
        }

        return mav;
    }

    @PutMapping("/{userId}/update")
    public String modifyProfile(ModifiedUserParam modifiedUserParam,
                                HttpServletRequest request,
                                HttpSession session) {

        logRequestInfo(request);

        User updateUser = userService.update(modifiedUserParam);
        session.setAttribute("userInfo", updateUser);
        return "redirect:/users";
    }

    private void logRequestInfo(HttpServletRequest request) {
        Map<String, Object> params = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(name -> params.put(name, request.getParameter(name)));

        log.debug("{} {} {}", request.getMethod(), request.getRequestURI(), params);
    }

    @ExceptionHandler({UserDomainException.class})
    private ResponseEntity<String> except(UserDomainException ex) {
        return new ResponseEntity<>(ex.getMessage(), ex.getHttpStatus());
    }
}
