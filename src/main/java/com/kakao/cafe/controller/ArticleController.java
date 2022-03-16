package com.kakao.cafe.controller;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.dto.ModifiedArticleParam;
import com.kakao.cafe.dto.NewArticleParam;
import com.kakao.cafe.exception.article.ArticleDomainException;
import com.kakao.cafe.service.ArticleService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("/write")
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
    public ModelAndView goUpdateForm(@PathVariable int id, ModelAndView mav) {
        mav.setViewName("qna/updateForm");
        mav.addObject("article", articleService.search(id));
        return mav;
    }

    @PutMapping("/{id}/update")
    public String modifyArticle(ModifiedArticleParam modifiedArticleParam) {
        articleService.update(modifiedArticleParam);
        return "redirect:/";
    }

    @DeleteMapping("/{id}/remove")
    public String removeArticle(@PathVariable int id) {
        articleService.remove(id);
        return "redirect:/";
    }

    @ExceptionHandler({ArticleDomainException.class})
    private ResponseEntity<String> except(ArticleDomainException ex) {
        return new ResponseEntity<>(ex.getMessage(), ex.getHttpStatus());
    }
}
