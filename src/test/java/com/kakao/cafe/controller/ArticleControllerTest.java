package com.kakao.cafe.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.kakao.cafe.domain.article.Article;
import com.kakao.cafe.service.ArticleService;

@WebMvcTest(controllers = ArticleController.class)
class ArticleControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ArticleService articleService;

    @DisplayName("/로 GET 요청이 오면 articles가 조회되어 index 뷰로 전달된다.")
    @Test
    void get_index() throws Exception {
        mvc.perform(get("/"))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("articles"))
            .andExpect(view().name("index"));
    }

    @DisplayName("/questions로 POST 요청을 하면 질문이 저장되고 /로 리다이렉트 된다.")
    @Test
    void post_questions() throws Exception {
        mvc.perform(post("/questions"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/"));
    }

    @DisplayName("/articles/{index}로 GET 요청을 하면 해당 index에 해당하는 Article로 이동한다.")
    @Test
    void get_article() throws Exception {
        // given && when
        given(articleService.showArticle(1))
            .willReturn(new Article("writer1", "title1", "contents1"));

        // then
        mvc.perform(get("/articles/1"))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("article"))
            .andExpect(view().name("qna/showQna"));
    }

}