package com.kakao.cafe.unit.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.kakao.cafe.controller.ArticleController;
import com.kakao.cafe.domain.Article;
import com.kakao.cafe.service.ArticleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(ArticleController.class)
public class ArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArticleService articleService;

    @Test
    @DisplayName("글을 작성하고 업로드한다")
    public void createTest() throws Exception {
        // given
        Article article = new Article("writer", "title", "content");

        given(articleService.write(article))
            .willReturn(article);

        // when
        ResultActions actions = mockMvc.perform(post("/questions")
            .param("writer", article.getWriter())
            .param("title", article.getTitle())
            .param("content", article.getContent())
            .accept(MediaType.parseMediaType("application/html;charset=UTF-8")));

        // then
        actions.andExpect(status().is3xxRedirection())
            .andExpect(model().attribute("article", article))
            .andExpect(view().name("redirect:/"));
    }
}
