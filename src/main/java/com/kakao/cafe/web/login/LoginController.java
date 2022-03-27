package com.kakao.cafe.web.login;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.service.LoginService;
import com.kakao.cafe.web.SessionConst;
import com.kakao.cafe.web.login.dto.LoginDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    private final LoginService loginService;
    private final Logger log = LoggerFactory.getLogger(LoginController.class);

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/login")
    public String getLoginForm(@ModelAttribute("form") LoginDto form) {
        log.info("get login form");
        return "user/login";
    }

    @PostMapping("/login")
    public String doLogin(@Validated @ModelAttribute("form") LoginDto form, BindingResult bindingResult,
                          @RequestParam(defaultValue = "/") String redirectURL,
                          HttpServletRequest request, HttpServletResponse response) {
        log.info("into doLogin : {}, {}", form.getUserId(), form.getPassword());

        if (bindingResult.hasErrors()) {
            log.info("doLogin form error: {}", bindingResult.getAllErrors());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return "user/login";
        }

        User findUser = loginService.login(form);

        if (findUser == null) {
            log.info("doLogin login error: {}", findUser);
            bindingResult.reject("notfound", "아이디 또는 비밀번호를 다시한번 확인해 주십시오.");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return "user/login";
        }

        // 세션이 필요한 시점에 생성하도록 배치
        HttpSession session = request.getSession();
        // 세션에 회원 저장
        session.setAttribute(SessionConst.SESSIONED_USER, findUser);
        log.info("로그인 성공 : {}", findUser);

        return "redirect:" + redirectURL;
    }

    @PostMapping("/logout")
    public String doLogout(HttpServletRequest request) {
        loginService.logout(request.getSession(false));
        return "redirect:/";
    }
}
