package com.kakao.cafe.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakao.cafe.controller.dto.ArticleSaveRequestDto;
import com.kakao.cafe.domain.article.Article;
import com.kakao.cafe.service.ArticleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ArticleController.class)
class ArticleControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    ArticleService articleService;

    ArticleSaveRequestDto dto;

    @BeforeEach
    void setUp() {
        String writer = "user";
        String title = "title";
        String contents = "contents";

        dto = new ArticleSaveRequestDto(writer, title, contents);
    }

    @Test
    @DisplayName("게시글 작성 페이지가 출력된다")
    void articlePageTest() throws Exception {
        mvc.perform(get("/qna"))
                .andExpect(status().isOk())
                .andExpect(view().name("qna/form"));
    }

    @Test
    @DisplayName("게시글이 작성된다")
    void articleSaveTest() throws Exception {
        //when
        mvc.perform(post("/questions")
                        .content(new ObjectMapper().writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    @DisplayName("게시글이 조회된다")
    void articleDetailTest() throws Exception {
        // given
        Article article = dto.toEntity();
        Long id = 0L;
        article.setId(id);
        article.setCreatedDate(new Date().toString());
        String url = "/articles/" + article.getId();

        given(articleService.findById(article.getId())).willReturn(article);

        // when
        mvc.perform(get(url))
                // then
                .andExpect(status().isOk())
                .andExpect(view().name("qna/show"))
                .andExpect(model().attribute("article", article));
    }
}
