package com.kakao.cafe.web;

import com.kakao.cafe.domain.posts.Posts;
import com.kakao.cafe.service.PostsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/qna")
public class PostsController {
    private static Logger log = LoggerFactory.getLogger(PostsController.class);
    private final PostsService postsService;

    @Autowired
    public PostsController(PostsService postsService) {
        this.postsService = postsService;
    }

    @GetMapping("/form")
    public String form() {
        log.info("qna form");
        return "qna/form";
    }

    @PostMapping("/form")
    public String registration(Posts posts) {
        postsService.qnaRegistration(posts);
        log.info("posts Id = {}, UserId= {}", posts.getId(), posts.getWriter());
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String showContent(@PathVariable Long id, Model model) {
        Posts posts = postsService.qnaShowContent(id);
        model.addAttribute("posts", posts);
        log.info("posts Id = {}, UserId= {}", posts.getId(), posts.getWriter());
        return "qna/show";
    }
}
