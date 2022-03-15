package com.kakao.cafe.web;

import com.kakao.cafe.domain.posts.Post;
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
public class PostController {
    private static Logger log = LoggerFactory.getLogger(PostController.class);
    private final PostsService postsService;

    @Autowired
    public PostController(PostsService postsService) {
        this.postsService = postsService;
    }

    @GetMapping("/form")
    public String form() {
        log.info("게시글 등록하기 폼 불러오기");
        return "qna/form";
    }

    @PostMapping("/form")
    public String registration(Post post) {
        log.info("게시글 등록하기");
        postsService.qnaRegister(post);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String showContent(@PathVariable Long id, Model model) {
        log.info("게시글 번호 = {} 로 게시글 상세확인하기~", id);
        model.addAttribute("post", postsService.findPost(id));
        return "qna/show";
    }
}
