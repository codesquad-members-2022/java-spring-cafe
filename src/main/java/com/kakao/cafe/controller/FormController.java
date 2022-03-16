package com.kakao.cafe.controller;

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
    public String qnaForm() {
        logger.info("GET /qnaForm");
        return "qna/formQna";
    }
}
