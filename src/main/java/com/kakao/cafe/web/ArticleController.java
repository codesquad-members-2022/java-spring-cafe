package com.kakao.cafe.web;

import com.kakao.cafe.exception.ClientException;
import com.kakao.cafe.service.ArticleService;
import com.kakao.cafe.service.ReplyService;
import com.kakao.cafe.web.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/qna")
public class ArticleController {

    private final Logger logger = LoggerFactory.getLogger(ArticleController.class);

    private final ArticleService articleService;
    private final ReplyService replyService;

    public ArticleController(ArticleService articleService, ReplyService replyService) {
        this.articleService = articleService;
        this.replyService = replyService;
    }

    @GetMapping("/write-qna")
    public String writeForm() {
        logger.info("User in qna-form");

        return "qna/form";
    }

    @PostMapping("/write-qna")
    public String write(ArticleDto articleDto, HttpSession httpSession) {
        SessionUser sessionedUser = SessionUser.from(httpSession);
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

        List<ReplyResponseDto> replyResponseDtos = replyService.showAllInArticle(id);
        model.addAttribute("size", replyResponseDtos.size());
        if(!replyResponseDtos.isEmpty()) {
            model.addAttribute("replies", replyResponseDtos);
        }

        logger.info("Show article[{}] with [{}] replies ", result, replyResponseDtos.size());

        return "qna/show";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteArticle(@PathVariable Integer id, HttpSession httpSession) {
        SessionUser sessionedUser = SessionUser.from(httpSession);
        String sessionedUserId = sessionedUser.getUserId();
        checkDeletable(id, sessionedUserId);
        articleService.deleteOne(id, sessionedUserId);
        logger.info("[{}] delete qna{}", sessionedUserId, id);

        return "redirect:/qna/all";
    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Integer id, HttpSession httpSession, Model model) {
        SessionUser sessionedUser = SessionUser.from(httpSession);
        logger.info("[{}] request updateForm qna{}", sessionedUser.getUserId(), id);

        ArticleResponseDto result = articleService.findOne(id);
        checkAccessPermission(result, sessionedUser);

        model.addAttribute("article", result);
        return "qna/update_form";
    }

    @PutMapping("/update/{id}")
    public String updateArticle(@PathVariable Integer id, ArticleUpdateDto articleUpdateDto, HttpSession httpSession) {
        SessionUser sessionedUser = SessionUser.from(httpSession);
        articleService.updateOne(sessionedUser.getUserId(), articleUpdateDto);
        logger.info("[{}] update qna[{}]", sessionedUser.getUserId(), id);

        return "redirect:/qna/show/" + id;
    }

    // session정보와 pathArticleId 확인
    private void checkAccessPermission(ArticleResponseDto articleResponseDto, SessionUser sessionedUser) {
        String writer = articleResponseDto.getWriter();
        Integer id = articleResponseDto.getId();
        if(!sessionedUser.hasSameId(writer)){
            logger.error("[{}] tries access [{}]'s article[{}]", sessionedUser.getUserId(), writer, id);
            throw new ClientException(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.");
        }
    }

    private void checkDeletable(Integer id, String userId) {
        if(!replyService.isDeletableArticle(id, userId)) {
            logger.error("[{}] failed to delete article[{}] with other user's replies", userId, id);
            throw new ClientException(HttpStatus.CONFLICT, "다른 유저의 답변이 포함되어 있어서 질문을 삭제할 수 없습니다.");
        }
    }
}
