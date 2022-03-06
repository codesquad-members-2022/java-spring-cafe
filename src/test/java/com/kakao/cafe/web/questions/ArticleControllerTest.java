package com.kakao.cafe.web.questions;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.service.ArticleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ArticleController.class)
class ArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArticleService mockArticleService;

    private Article article1, article2;

    @BeforeEach
    void setUp() {
        article1 = new Article("testA", "title", "content", LocalDateTime.now());
        article2 = new Article("testB", "titleB", "contentB", LocalDateTime.now());
        article1.setId(1L);
        article2.setId(2L);
    }

    @Test
    @DisplayName("폼을 요청하는 테스트")
    void requestFormArticleTest() throws Exception {
        // when
        ResultActions requestThenResult = mockMvc.perform(get("/questions")
                .accept(MediaType.TEXT_HTML_VALUE));

        // then
        requestThenResult.andExpect(status().isOk())
                .andExpect(view().name("/qna/form"));

    }

    @Test
    @DisplayName("글을 작성하고 Post 요청 보내기")
    void getFormArticleTest() throws Exception {
        // when
        ResultActions requestThenResult = mockMvc.perform(post("/questions")
                .param("writer", "postTest")
                .param("title", "title")
                .param("contents", "content")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .accept(MediaType.TEXT_HTML_VALUE));

        // then
        requestThenResult.andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }

    @Test
    void showSpecificArticleTest() throws Exception {
        // given
        given(mockArticleService.findArticleById(any())).willReturn(article1);

        // when
        ResultActions requestThenResult = mockMvc.perform(get("/questions/" + article1.getId())
                .accept(MediaType.TEXT_HTML_VALUE));

        // then
        requestThenResult.andExpect(status().isOk())
                .andExpect(model().attribute("article", article1))
                .andExpect(view().name("/qna/show"));
    }
}
