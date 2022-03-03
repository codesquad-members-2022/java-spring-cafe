package com.kakao.cafe.unit.controller;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.kakao.cafe.controller.ArticleController;
import com.kakao.cafe.domain.Article;
import com.kakao.cafe.exception.CustomException;
import com.kakao.cafe.exception.ErrorCode;
import com.kakao.cafe.service.ArticleService;
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
public class ArticleControllerTest {

    private static final Integer ARTICLE_ID = 1;
    private static final String WRITER = "writer";
    private static final String TITLE = "title";
    private static final String CONTENTS = "contents";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArticleService articleService;

    private Article article;

    @BeforeEach
    public void setUp() {
        article = new Article(WRITER, TITLE, CONTENTS);
    }


    @Test
    @DisplayName("글을 작성하는 화면을 보여준다")
    public void createArticleFormTest() throws Exception {
        // when
        ResultActions actions = mockMvc.perform(get("/questions")
            .accept(MediaType.parseMediaType("application/html;charset=UTF-8")));

        // then
        actions.andExpect(status().isOk())
            .andExpect(view().name("qna/form"));
    }

    @Test
    @DisplayName("글을 작성하고 업로드한다")
    public void createArticleTest() throws Exception {
        // given
        given(articleService.write(any()))
            .willReturn(article);

        // when
        ResultActions actions = mockMvc.perform(post("/questions")
            .param("writer", WRITER)
            .param("title", TITLE)
            .param("contents", CONTENTS)
            .accept(MediaType.parseMediaType("application/html;charset=UTF-8")));

        // then
        actions.andExpect(status().is3xxRedirection())
            .andExpect(model().attribute("article", allOf(
                hasProperty("writer", is(WRITER)),
                hasProperty("title", is(TITLE))
            )))
            .andExpect(view().name("redirect:/"));
    }

    @Test
    @DisplayName("등록된 모든 글을 화면에 출력한다")
    public void listArticlesTest() throws Exception {
        // given
        article.setArticleId(ARTICLE_ID);

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

    @Test
    @DisplayName("질문 id 로 선택한 질문을 화면에 출력한다")
    public void showArticleTest() throws Exception {
        // given
        article.setArticleId(ARTICLE_ID);

        given(articleService.findArticle(any()))
            .willReturn(article);

        // when
        ResultActions actions = mockMvc.perform(get("/articles/" + article.getArticleId())
            .accept(MediaType.parseMediaType("application/html;charset=UTF-8")));

        // then
        actions.andExpect(status().isOk())
            .andExpect(model().attribute("article", article))
            .andExpect(view().name("qna/show"));
    }

    @Test
    @DisplayName("존재하지 않은 질문 id 로 질문을 조회하면 예외 페이지로 이동한다")
    public void showArticleValidateTest() throws Exception {
        // given
        CustomException exception = new CustomException(ErrorCode.ARTICLE_NOT_FOUND);

        given(articleService.findArticle(any()))
            .willThrow(exception);

        // when
        ResultActions actions = mockMvc.perform(get("/articles/1")
            .accept(MediaType.parseMediaType("application/html;charset=UTF-8")));

        // then
        actions.andExpect(status().isOk())
            .andExpect(model().attribute("status", exception.getErrorCode().getHttpStatus()))
            .andExpect(model().attribute("message", exception.getErrorCode().getMessage()))
            .andExpect(view().name("error/index"));
    }

}
