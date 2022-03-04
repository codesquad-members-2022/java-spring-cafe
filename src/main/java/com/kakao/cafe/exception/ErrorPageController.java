package com.kakao.cafe.exception;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("error")
public class ErrorPageController {

    @RequestMapping("/404")
    public String errorPage404(HttpServletRequest request) {
        return "error/404";
    }
}
