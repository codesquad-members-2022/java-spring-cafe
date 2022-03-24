package com.kakao.cafe.web;


import com.kakao.cafe.constants.LoginConstants;
import com.kakao.cafe.exception.ClientException;
import com.kakao.cafe.service.ArticleService;
import com.kakao.cafe.web.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/qna")
public class ArticleController {

    private final Logger logger = LoggerFactory.getLogger(ArticleController.class);

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/write-qna")
    public String writeForm() {
        logger.info("User in qna-form");

        return "qna/form";
    }

    @PostMapping("/write-qna")
    public String write(ArticleDto articleDto, HttpSession httpSession) {
        UserResponseDto sessionedUser = (UserResponseDto) httpSession.getAttribute(LoginConstants.SESSIONED_USER);
        String sessionedUserId = sessionedUser.getUserId();
        logger.info("[{}] writing qna{}", sessionedUserId, articleDto);
        articleService.write(sessionedUserId, articleDto);

        return "redirect:/qna/all";
    }

    @GetMapping("/all")
    public String showAll(Model model) {
        logger.info("Show all articles");
        model.addAttribute("articles", articleService.findAll());

        return "qna/list";
    }

    @GetMapping("/show/{id}")
    public String showArticle(@PathVariable Integer id, Model model) {
        logger.info("Search for articleId{} to show client", id);

        ArticleResponseDto result = articleService.findOne(id);
        model.addAttribute("article", result);
        logger.info("Show article{}", result);

        return "qna/show";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteArticle(@PathVariable Integer id, HttpSession httpSession) {
        UserResponseDto sessionedUser = (UserResponseDto) httpSession.getAttribute(LoginConstants.SESSIONED_USER);
        String sessionedUserUserId = sessionedUser.getUserId();

        articleService.deleteOne(id, sessionedUserUserId);
        logger.info("[{}] delete qna{}", sessionedUserUserId, id);

        return "redirect:/qna/all";
    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Integer id, HttpSession httpSession, Model model) {
        SessionUser sessionedUser = (SessionUser) httpSession.getAttribute(LoginConstants.SESSIONED_USER);
        logger.info("[{}] request updateForm qna{}", sessionedUser.getUserId(), id);

        ArticleResponseDto result = articleService.findOne(id);
        checkAccessPermission(result, sessionedUser);

        model.addAttribute("article", result);
        return "qna/update_form";
    }

    @PutMapping("/update/{id}")
    public String updateArticle(@PathVariable Integer id, ArticleUpdateDto articleUpdateDto, HttpSession httpSession) {
        UserResponseDto sessionedUser = (UserResponseDto) httpSession.getAttribute(LoginConstants.SESSIONED_USER);
        articleService.updateOne(sessionedUser.getUserId(), articleUpdateDto);

        return "redirect:/qna/show/" + id;
    }

    // session정보와 pathArticleId 확인
    private void checkAccessPermission(ArticleResponseDto articleResponseDto, SessionUser sessionedUser) {
        String writer = articleResponseDto.getWriter();
        Integer id = articleResponseDto.getId();
        if(!sessionedUser.hasSameId(writer)){
            logger.info("[{}] tries access [{}]'s article[{}]", sessionedUser.getUserId(), writer, id);
            throw new ClientException(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.");
        }
    }
}
