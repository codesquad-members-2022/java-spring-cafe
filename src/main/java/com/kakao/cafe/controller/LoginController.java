package com.kakao.cafe.controller;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.dto.LoginParam;
import com.kakao.cafe.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/login")
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    public String login(LoginParam loginParam, HttpSession session, HttpServletRequest request) {

        logRequestInfo(request);

        User user = loginService.checkInfo(loginParam);
        if (!user.confirmPassword(loginParam.getPassword())) {
            return "user/login_failed";
        }

        session.setAttribute("userInfo", user);
        return "redirect:/users";
    }

    private void logRequestInfo(HttpServletRequest request) {
        Map<String, Object> params = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(name -> params.put(name, request.getParameter(name)));

        log.debug("{} {} {}", request.getMethod(), request.getRequestURI(), params);
    }
}
