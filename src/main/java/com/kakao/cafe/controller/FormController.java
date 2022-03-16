package com.kakao.cafe.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FormController {

    private final Logger logger = LoggerFactory.getLogger(FormController.class);

    @GetMapping("/form")
    public String form() {
        logger.info("GET /form");
        return "user/form";
    }

    @GetMapping("/qnaForm")
    public String qnaForm(HttpServletRequest request) {
        logger.info("GET /qnaForm");
        HttpSession session = request.getSession();
        Object value = session.getAttribute("sessionUser");
        if (value == null) {
            return "redirect:/login";
        }
        return "qna/formQna";
    }
}
