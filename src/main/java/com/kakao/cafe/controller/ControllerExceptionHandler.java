package com.kakao.cafe.controller;

import com.kakao.cafe.controller.dto.ArticleDto;
import com.kakao.cafe.controller.dto.UserSaveDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class ControllerExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(IllegalArgumentException.class)
    public ModelAndView returnFormAndSendExceptionMessage(HttpServletRequest request, HttpServletResponse response, IllegalArgumentException exception) throws IOException {
        logger.error("IllegalArgumentException={}", exception.getMessage());
        ModelAndView modelAndView = createModelAndView(exception, request);
        if (!modelAndView.hasView()) {
            response.sendError(404, "페이지를 찾을 수 없습니다");
        }
        return modelAndView;
    }

    private ModelAndView createModelAndView(IllegalArgumentException exception,HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("exception", exception.getMessage());

        String requestURI = request.getRequestURI();
        if (requestURI.equals("/users")) {
            UserSaveDto userSaveDto = changeParamsToUserDto(request);
            modelAndView.addObject(userSaveDto);
            modelAndView.setViewName("user/form");
        }
        if (requestURI.equals("/questions")) {
            ArticleDto articleDto = changeParamsToArticleSaveDto(request);
            modelAndView.addObject(articleDto);
            modelAndView.setViewName("qna/form");
        }
        return modelAndView;
    }

    private ArticleDto changeParamsToArticleSaveDto(HttpServletRequest request) {
        ArticleDto articleDto = new ArticleDto();
        articleDto.setUserId(request.getParameter("userId"));
        articleDto.setTitle(request.getParameter("title"));
        articleDto.setContents(request.getParameter("contents"));
        return articleDto;
    }

    private UserSaveDto changeParamsToUserDto(HttpServletRequest request) {
        UserSaveDto userSaveDto = new UserSaveDto();
        userSaveDto.setUserId(request.getParameter("userId"));
        userSaveDto.setPassword(request.getParameter("password"));
        userSaveDto.setName(request.getParameter("name"));
        userSaveDto.setEmail(request.getParameter("email"));
        return userSaveDto;
    }
}
