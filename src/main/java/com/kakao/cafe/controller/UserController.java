package com.kakao.cafe.controller;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.service.UserService;
import com.kakao.cafe.service.VolatilityUserService;
import org.apache.catalina.connector.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService service;

    public UserController(VolatilityUserService service) {
        this.service = service;
    }

    @PostMapping("/user/create")
    public String signUp(HttpServletRequest request,
                         User user) {

        logAPIInfo(request);

        service.addUser(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public ModelAndView getUserList(HttpServletRequest request,
                                    HttpServletResponse response,
                                    ModelAndView mav) {
        logAPIInfo(request);
        setResponseInfo(response);

        mav.setViewName("user/list");
        mav.addObject("users", service.findAll());
        return mav;
    }

    @GetMapping("/users/{userId}")
    public ModelAndView getUserProfile(HttpServletRequest request,
                                       HttpServletResponse response,
                                       @PathVariable String userId,
                                       ModelAndView mav) {

        logAPIInfo(request);
        setResponseInfo(response);

        mav.setViewName("user/profile");
        mav.addObject("user", service.findUser(userId));
        return mav;
    }

    private void logAPIInfo(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        Map<String, Object> params = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(name -> params.put(name, request.getParameter(name)));

        log.info("[{}] [{}] {}", method, requestURI, params);
    }

    private void setResponseInfo(HttpServletResponse response) {
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
    }
}
