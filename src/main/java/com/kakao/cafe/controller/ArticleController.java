package com.kakao.cafe.controller;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.dto.ModifiedArticleParam;
import com.kakao.cafe.dto.NewArticleParam;
import com.kakao.cafe.exception.article.ArticleDomainException;
import com.kakao.cafe.exception.common.AccessRestrictionException;
import com.kakao.cafe.exception.common.CommonException;
import com.kakao.cafe.service.ArticleService;
import com.kakao.cafe.session.SessionUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import static com.kakao.cafe.message.ArticleDomainMessage.DELETE_ACCESS_RESTRICTION_MESSAGE;
import static com.kakao.cafe.message.ArticleDomainMessage.MODIFY_ACCESS_RESTRICTION_MESSAGE;

@Controller
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping
    public String writeArticle(NewArticleParam newArticleParam) {
        articleService.add(newArticleParam);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public ModelAndView getDetail(@PathVariable int id, ModelAndView mav) {
        mav.setViewName("qna/show");
        mav.addObject("article", articleService.search(id));
        return mav;
    }

    @GetMapping("/{id}/form")
    public ModelAndView goUpdateForm(@PathVariable int id,
                                     @SessionAttribute("sessionUser") SessionUser sessionUser,
                                     ModelAndView mav) {

        Article article = articleService.search(id);
        String userId = article.getWriter();

        if (!sessionUser.ownerOf(userId)) {
            throw new AccessRestrictionException(HttpStatus.FORBIDDEN, "error/4xx", MODIFY_ACCESS_RESTRICTION_MESSAGE);
        }

        mav.setViewName("qna/updateForm");
        mav.addObject("article", articleService.search(id));
        return mav;
    }

    @PutMapping("/{id}")
    public String modifyArticle(ModifiedArticleParam modifiedArticleParam,
                                @SessionAttribute("sessionUser") SessionUser sessionUser) {

        String userId = modifiedArticleParam.getWriter();

        if (!sessionUser.ownerOf(userId)) {
            throw new AccessRestrictionException(HttpStatus.FORBIDDEN, "error/4xx", MODIFY_ACCESS_RESTRICTION_MESSAGE);
        }

        articleService.update(modifiedArticleParam);
        return "redirect:/";
    }

    @DeleteMapping("/{id}")
    public String removeArticle(@PathVariable int id,
                                @SessionAttribute("sessionUser") SessionUser sessionUser) {

        Article article = articleService.search(id);
        String userId = article.getWriter();

        if (!sessionUser.ownerOf(userId)) {
            throw new AccessRestrictionException(HttpStatus.FORBIDDEN, "error/4xx", DELETE_ACCESS_RESTRICTION_MESSAGE);
        }

        articleService.remove(id);
        return "redirect:/";
    }

    @ExceptionHandler({ArticleDomainException.class})
    private ResponseEntity<String> except(ArticleDomainException ex) {
        return new ResponseEntity<>(ex.getMessage(), ex.getHttpStatus());
    }

    @ExceptionHandler({CommonException.class})
    private ModelAndView goErrorPage(CommonException ex) {
        ModelAndView mav = new ModelAndView(ex.getViewName());
        mav.setStatus(ex.getHttpStatus());
        mav.addObject("message", ex.getMessage());
        return mav;
    }
}
