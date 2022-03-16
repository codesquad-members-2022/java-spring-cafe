package com.kakao.cafe.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FormController {

    private static final String NO_LOGIN_STATUS = "[ERROR] 로그인 후 확인할 수 있습니다.";

    private final Logger logger = LoggerFactory.getLogger(FormController.class);

    @GetMapping("/form")
    public String form() {
        logger.info("GET /form");
        return "user/form";
    }

    @GetMapping("/qnaForm")
    public String qnaForm(HttpServletRequest request) {
        findUserFromSession(request);
        logger.info("GET /qnaForm");
        return "qna/formQna";
    }

    private void findUserFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object value = session.getAttribute("sessionUser");
        if (value == null) {
            throw new IllegalArgumentException(NO_LOGIN_STATUS);
        }
    }
}
