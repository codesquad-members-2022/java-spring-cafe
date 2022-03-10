package com.kakao.cafe.web;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class MainController {
    private static final String LOGIN_USER_KEY = "SESSIONED_USER";
    private final ArticleService articleService;

    public MainController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/")
    public String main(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        model.addAttribute("articles", articleService.findArticles());

        if (session == null) {
            return "index";
        }

        User loginUser = (User) session.getAttribute(LOGIN_USER_KEY);

        if (loginUser == null) {
            return "index";
        }

        model.addAttribute("user", loginUser);
        return "index";
    }
}
