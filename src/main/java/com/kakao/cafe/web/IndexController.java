package com.kakao.cafe.web;

import com.kakao.cafe.domain.user.User;
import com.kakao.cafe.service.PostsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class IndexController {
    private static final Logger log = LoggerFactory.getLogger(IndexController.class);
    private final PostsService postsService;

    @Autowired
    public IndexController(PostsService postsService) {
        this.postsService = postsService;
    }

    @GetMapping("/")
    public String index(Model model, HttpServletRequest request) {
        log.info("인덱스 페이지 불러오기");
        HttpSession session = request.getSession(false);
        model.addAttribute("post", postsService.findPosts());

        if (session == null) {
            return "index";
        }
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "index";
        }
        model.addAttribute("user", loginUser);
        return "index";
    }
}
