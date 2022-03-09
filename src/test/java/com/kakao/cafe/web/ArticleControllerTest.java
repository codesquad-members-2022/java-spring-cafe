package com.kakao.cafe.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.service.ArticleService;
import com.kakao.cafe.web.dto.ArticleDetailDto;
import com.kakao.cafe.web.dto.ArticleListDto;
import com.kakao.cafe.web.dto.ArticleRegisterFormDto;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(ArticleController.class)
class ArticleControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ArticleService articleService;

    @Test
    @DisplayName("게시글 등록이 수행된다.")
    void 게시글_등록_테스트() throws Exception {
        // given
        ArticleRegisterFormDto articleRegisterFormDto = new ArticleRegisterFormDto("testWriter",
            "testTitle", "testContents");
        doNothing().when(articleService)
            .register(any());

        // when
        ResultActions resultAction = mvc.perform(post("/questions")
            .param("writer", "testWriter")
            .param("title", "testTitle")
            .param("contents", "testContents"));

        // then
        resultAction.andExpect(redirectedUrl("/"));
    }

    @Test
    @DisplayName("모든 게시글 목록을 출력한다.")
    void 게시글_목록_출력_테스트() throws Exception {
        // given
        Article article = new Article.ArticleBuilder()
            .setTitle("testTitle")
            .setContents("testContents")
            .setWriter("testWriter")
            .build();
        article.setCreatedTime("testDate");
        ArticleListDto articleListDto = new ArticleListDto(article);

        given(articleService.showAll())
            .willReturn(List.of(articleListDto));

        // when
        ResultActions resultActions = mvc.perform(get("/"));

        // then
        resultActions.andExpect(status().isOk())
            .andExpect(model().attribute("questions", List.of(articleListDto)))
            .andExpect(view().name("index"));
    }

    @Test
    @DisplayName("아이드를 통해 게시글을 상세 조회한다.")
    void 게시글_상세_조회_테스트() throws Exception {
        // given
        Article article = new Article.ArticleBuilder()
            .setTitle("testTitle")
            .setContents("testContents")
            .setWriter("testWriter")
            .build();
        article.setCreatedTime("testDate");
        ArticleDetailDto articleDetailDto = new ArticleDetailDto(article);

        given(articleService.showOne("1"))
            .willReturn(articleDetailDto);

        // when
        ResultActions resultActions = mvc.perform(get("/questions/1"));

        // then
        resultActions.andExpect(model().attribute("writer", articleDetailDto.getWriter()))
            .andExpect(model().attribute("title", articleDetailDto.getTitle()))
            .andExpect(model().attribute("contents", articleDetailDto.getContents()))
            .andExpect(model().attribute("createdTime", articleDetailDto.getCreatedTime()))
            .andExpect(view().name("qna/show"));
    }
}
