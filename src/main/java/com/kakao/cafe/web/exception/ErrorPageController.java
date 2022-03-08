package com.kakao.cafe.web.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorPageController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/error-page/404")
    public String errorPage404(HttpServletRequest request) {
        logger.info(request.getRequestURI());
        return "error-pages/404";
    }

    @RequestMapping("/error-page/500")
    public String errorPage500(HttpServletRequest request) {
        logger.info(request.getRequestURI());
        return "error-pages/500";
    }
}
