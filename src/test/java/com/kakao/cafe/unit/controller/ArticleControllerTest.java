package com.kakao.cafe.unit.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.doThrow;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.kakao.cafe.controller.ArticleController;
import com.kakao.cafe.dto.ArticleResponse;
import com.kakao.cafe.dto.ReplyResponse;
import com.kakao.cafe.exception.ErrorCode;
import com.kakao.cafe.exception.InvalidRequestException;
import com.kakao.cafe.exception.NotFoundException;
import com.kakao.cafe.service.ArticleService;
import com.kakao.cafe.service.ReplyService;
import com.kakao.cafe.session.SessionUser;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.servlet.HandlerInterceptor;

@WebMvcTest(ArticleController.class)
@DisplayName("ArticleController 단위 테스트")
public class ArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HandlerInterceptor interceptor;

    @MockBean
    private ArticleService articleService;

    @MockBean
    private ReplyService replyService;

    private MockHttpSession session;
    private ArticleResponse articleResponse;
    private SessionUser sessionUser;
    private ReplyResponse replyResponse;

    @BeforeEach
    public void setUp() throws Exception {
        given(interceptor.preHandle(any(), any(), any())).willReturn(true);

        replyResponse = new ReplyResponse(1, 1, "writer", "comment", LocalDateTime.now());

        articleResponse = new ArticleResponse(1, "writer", "title", "contents", LocalDateTime.now(),
            List.of(replyResponse), 1);

        sessionUser = new SessionUser(1, "writer", "userPassword", "userName",
            "user@example.com");

        session = new MockHttpSession();
        session.setAttribute(SessionUser.SESSION_KEY, sessionUser);
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
        given(articleService.write(any(), any()))
            .willReturn(articleResponse);

        // when
        ResultActions actions = mockMvc.perform(post("/questions")
            .session(session)
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
        ResultActions actions = performGet("/articles/0");

        // then
        actions.andExpect(status().isOk())
            .andExpect(model().attribute("status", ErrorCode.ARTICLE_NOT_FOUND.getHttpStatus()))
            .andExpect(model().attribute("message", ErrorCode.ARTICLE_NOT_FOUND.getMessage()))
            .andExpect(view().name("error/index"));
    }

    @Test
    @DisplayName("세션 정보와 질문 id 로 유저의 질문을 조회하고 변경 폼으로 이동한다")
    public void formUpdateArticleTest() throws Exception {
        // given
        given(articleService.mapUserArticle(any(), any()))
            .willReturn(articleResponse);

        // when
        ResultActions actions = mockMvc.perform(get("/articles/1/form")
            .session(session)
            .accept(MediaType.TEXT_HTML));

        // then
        actions.andExpect(status().isOk())
            .andExpect(model().attribute("article", articleResponse))
            .andExpect(view().name("qna/form"));
    }

    @Test
    @DisplayName("세션 정보와 존재하지 않는 질문 id 로 유저의 질문을 조회하면 에러페이지로 이동한다")
    public void formUpdateArticleNotFoundTest() throws Exception {
        // given
        given(articleService.mapUserArticle(any(), any()))
            .willThrow(new NotFoundException(ErrorCode.ARTICLE_NOT_FOUND));

        // when
        ResultActions actions = mockMvc.perform(get("/articles/1/form")
            .session(session)
            .accept(MediaType.TEXT_HTML));

        // then
        actions.andExpect(status().isOk())
            .andExpect(model().attribute("status", ErrorCode.ARTICLE_NOT_FOUND.getHttpStatus()))
            .andExpect(model().attribute("message", ErrorCode.ARTICLE_NOT_FOUND.getMessage()))
            .andExpect(view().name("error/index"));
    }

    @Test
    @DisplayName("다른 세션 정보와 질문 id 로 유저의 질문을 조회하면 에러페이지로 이동한다")
    public void formUpdateArticleValidateTest() throws Exception {
        // given
        given(articleService.mapUserArticle(any(), any()))
            .willThrow(new NotFoundException(ErrorCode.INVALID_ARTICLE_WRITER));

        // when
        ResultActions actions = mockMvc.perform(get("/articles/1/form")
            .session(session)
            .accept(MediaType.TEXT_HTML));

        // then
        actions.andExpect(status().isOk())
            .andExpect(
                model().attribute("status", ErrorCode.INVALID_ARTICLE_WRITER.getHttpStatus()))
            .andExpect(model().attribute("message", ErrorCode.INVALID_ARTICLE_WRITER.getMessage()))
            .andExpect(view().name("error/index"));
    }

    @Test
    @DisplayName("세션 정보, 질문 변경 사항과 질문 id 로 유저의 질문을 업데이트하고 첫 페이지로 이동한다")
    public void updateArticleTest() throws Exception {
        // given
        given(articleService.updateArticle(any(), any(), any()))
            .willReturn(articleResponse);

        // when
        ResultActions actions = mockMvc.perform(put("/articles/1")
            .session(session)
            .param("title", "otherTitle")
            .param("contents", "otherContents")
            .accept(MediaType.TEXT_HTML));

        // then
        actions.andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/"));
    }

    @Test
    @DisplayName("세션 정보, 질문 변경 사항과 존재하지 않는 질문 id 로 유저의 질문 업데이트 시 에러 페이지로 이동한다")
    public void updateArticleNotFoundTest() throws Exception {
        // given
        given(articleService.updateArticle(any(), any(), any()))
            .willThrow(new NotFoundException(ErrorCode.ARTICLE_NOT_FOUND));

        // when
        ResultActions actions = mockMvc.perform(put("/articles/1")
            .session(session)
            .param("title", "otherTitle")
            .param("contents", "otherContents")
            .accept(MediaType.TEXT_HTML));

        // then
        actions.andExpect(status().isOk())
            .andExpect(model().attribute("status", ErrorCode.ARTICLE_NOT_FOUND.getHttpStatus()))
            .andExpect(model().attribute("message", ErrorCode.ARTICLE_NOT_FOUND.getMessage()))
            .andExpect(view().name("error/index"));
    }

    @Test
    @DisplayName("다른 유저의 세션 정보, 질문 변경 사항과 질문 id 로 유저의 질문 업데이트 시 에러 페이지로 이동한다")
    public void updateArticleValidateTest() throws Exception {
        // given
        given(articleService.updateArticle(any(), any(), any()))
            .willThrow(new InvalidRequestException(ErrorCode.INVALID_ARTICLE_WRITER));

        // when
        ResultActions actions = mockMvc.perform(put("/articles/1")
            .session(session)
            .param("title", "otherTitle")
            .param("contents", "otherContents")
            .accept(MediaType.TEXT_HTML));

        // then
        actions.andExpect(status().isOk())
            .andExpect(
                model().attribute("status", ErrorCode.INVALID_ARTICLE_WRITER.getHttpStatus()))
            .andExpect(model().attribute("message", ErrorCode.INVALID_ARTICLE_WRITER.getMessage()))
            .andExpect(view().name("error/index"));
    }

    @Test
    @DisplayName("세션 정보와 질문 id 로 유저의 질문을 삭제하고 첫 페이지로 이동한다")
    public void deleteArticleTest() throws Exception {
        // when
        ResultActions actions = mockMvc.perform(delete("/articles/1")
            .session(session)
            .accept(MediaType.TEXT_HTML));

        // then
        actions.andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/"));
    }

    @Test
    @DisplayName("세션 정보와 존재하지 않는 질문 id 로 유저의 질문을 삭제하면 에러 페이지로 이동한다")
    public void deleteArticleNotFoundTest() throws Exception {
        // given
        doThrow(new NotFoundException(ErrorCode.ARTICLE_NOT_FOUND))
            .when(articleService).deleteArticle(any(), any());

        // when
        ResultActions actions = mockMvc.perform(delete("/articles/1")
            .session(session)
            .accept(MediaType.TEXT_HTML));

        // then
        actions.andExpect(status().isOk())
            .andExpect(model().attribute("status", ErrorCode.ARTICLE_NOT_FOUND.getHttpStatus()))
            .andExpect(model().attribute("message", ErrorCode.ARTICLE_NOT_FOUND.getMessage()))
            .andExpect(view().name("error/index"));
    }

    @Test
    @DisplayName("세션 정보와 존재하지 않는 질문 id 로 유저의 질문을 삭제하면 에러 페이지로 이동한다")
    public void deleteArticleValidateTest() throws Exception {
        // given
        doThrow(new NotFoundException(ErrorCode.INVALID_ARTICLE_WRITER))
            .when(articleService).deleteArticle(any(), any());

        // when
        ResultActions actions = mockMvc.perform(delete("/articles/1")
            .session(session)
            .accept(MediaType.TEXT_HTML));

        // then
        actions.andExpect(status().isOk())
            .andExpect(
                model().attribute("status", ErrorCode.INVALID_ARTICLE_WRITER.getHttpStatus()))
            .andExpect(model().attribute("message", ErrorCode.INVALID_ARTICLE_WRITER.getMessage()))
            .andExpect(view().name("error/index"));
    }

    @Test
    @DisplayName("댓글을 작성하고 저장한 후 해당 페이지를 새로고침한다")
    public void createAnswerTest() throws Exception {
        // given
        given(replyService.comment(any(), any(), any()))
            .willReturn(replyResponse);

        // when
        ResultActions actions = mockMvc.perform(post("/articles/1/answers")
            .session(session)
            .param("comment", "comment")
            .accept(MediaType.TEXT_HTML));

        // then
        actions.andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/"));
    }
}
