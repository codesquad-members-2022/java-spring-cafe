package com.kakao.cafe.unit.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.kakao.cafe.controller.ArticleController;
import com.kakao.cafe.domain.Article;
import com.kakao.cafe.service.ArticleService;
import java.util.List;
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
    @DisplayName("글을 작성하는 화면을 보여준다")
    public void createArticleFormTest() throws Exception {
        // when
        ResultActions actions = mockMvc.perform(get("questions")
            .accept(MediaType.parseMediaType("application/html;charset=UTF-8")));

        // then
        actions.andExpect(status().isOk())
            .andExpect(view().name("qna/form"));
    }

    @Test
    @DisplayName("글을 작성하고 업로드한다")
    public void createArticleTest() throws Exception {
        // given
        Article article = new Article("writer", "title", "contents");

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

    @Test
    @DisplayName("등록된 모든 글을 화면에 출력한다")
    public void listArticlesTest() throws Exception {
        // given
        Article article = new Article("writer", "title", "contents");

        given(articleService.findArticles())
            .willReturn(List.of(article));

        // when
        ResultActions actions = mockMvc.perform(get("/")
            .accept(MediaType.parseMediaType("application/html;charset=UTF-8")));

        // then
        actions.andExpect(status().isOk())
            .andExpect(model().attribute("articles", List.of(article)))
            .andExpect(view().name("qna/list"));
    }

}
