package com.kakao.cafe.unit.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.kakao.cafe.controller.ArticleController;
import com.kakao.cafe.dto.ArticleResponse;
import com.kakao.cafe.exception.ErrorCode;
import com.kakao.cafe.exception.NotFoundException;
import com.kakao.cafe.service.ArticleService;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(ArticleController.class)
@DisplayName("ArticleController 단위 테스트")
public class ArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArticleService articleService;

    private ArticleResponse articleResponse;

    @BeforeEach
    public void setUp() {
        articleResponse = new ArticleResponse(1, "writer", "title", "contents",
            LocalDateTime.now());
    }

    private ResultActions performGet(String url) throws Exception {
        return mockMvc.perform(get(url).accept(MediaType.TEXT_HTML));
    }

    @Test
    @DisplayName("글을 작성하는 화면을 보여준다")
    public void createArticleFormTest() throws Exception {
        // when
        ResultActions actions = performGet("/questions");

        // then
        actions.andExpect(status().isOk())
            .andExpect(view().name("qna/form"));
    }

    @Test
    @DisplayName("글을 작성하고 업로드한다")
    public void createArticleTest() throws Exception {
        // given
        given(articleService.write(any()))
            .willReturn(articleResponse);

        // when
        ResultActions actions = mockMvc.perform(post("/questions")
            .param("writer", "writer")
            .param("title", "title")
            .param("contents", "contents")
            .accept(MediaType.TEXT_HTML));

        // then
        actions.andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/"));
    }

    @Test
    @DisplayName("등록된 모든 글을 화면에 출력한다")
    public void listArticlesTest() throws Exception {
        // given
        given(articleService.findArticles())
            .willReturn(List.of(articleResponse));

        // when
        ResultActions actions = performGet("/");

        // then
        actions.andExpect(status().isOk())
            .andExpect(model().attribute("articles", List.of(articleResponse)))
            .andExpect(view().name("qna/list"));
    }

    @Test
    @DisplayName("질문 id 로 선택한 질문을 화면에 출력한다")
    public void showArticleTest() throws Exception {
        // given
        given(articleService.findArticle(any()))
            .willReturn(articleResponse);

        // when
        ResultActions actions = performGet("/articles/1");

        // then
        actions.andExpect(status().isOk())
            .andExpect(model().attribute("article", articleResponse))
            .andExpect(view().name("qna/show"));
    }

    @Test
    @DisplayName("존재하지 않은 질문 id 로 질문을 조회하면 예외 페이지로 이동한다")
    public void showArticleValidateTest() throws Exception {
        // given
        given(articleService.findArticle(any()))
            .willThrow(new NotFoundException(ErrorCode.ARTICLE_NOT_FOUND));

        // when
        ResultActions actions = performGet("/articles/2");

        // then
        actions.andExpect(status().isOk())
            .andExpect(model().attribute("status", ErrorCode.ARTICLE_NOT_FOUND.getHttpStatus()))
            .andExpect(model().attribute("message", ErrorCode.ARTICLE_NOT_FOUND.getMessage()))
            .andExpect(view().name("error/index"));
    }

}
