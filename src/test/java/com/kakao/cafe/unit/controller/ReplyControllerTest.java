package com.kakao.cafe.unit.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.doThrow;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.kakao.cafe.controller.ReplyController;
import com.kakao.cafe.dto.ReplyResponse;
import com.kakao.cafe.exception.ErrorCode;
import com.kakao.cafe.exception.InvalidRequestException;
import com.kakao.cafe.exception.NotFoundException;
import com.kakao.cafe.service.ReplyService;
import com.kakao.cafe.session.SessionUser;
import java.time.LocalDateTime;
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

@WebMvcTest(ReplyController.class)
@DisplayName("ReplyController 단위 테스트")
public class ReplyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HandlerInterceptor interceptor;

    @MockBean
    private ReplyService replyService;

    private MockHttpSession session;
    private ReplyResponse replyResponse;
    private SessionUser sessionUser;

    @BeforeEach
    public void setUp() throws Exception {
        given(interceptor.preHandle(any(), any(), any())).willReturn(true);

        replyResponse = new ReplyResponse(1, 1, "writer", "comment", LocalDateTime.now());
        sessionUser = new SessionUser(1, "writer", "userPassword", "userName",
            "user@example.com");

        session = new MockHttpSession();
        session.setAttribute(SessionUser.SESSION_KEY, sessionUser);
    }

    @Test
    @DisplayName("댓글을 작성하고 저장한다")
    public void createAnswerTest() throws Exception {
        // given
        given(replyService.comment(sessionUser, 1, "comment"))
            .willReturn(replyResponse);

        // when
        ResultActions actions = mockMvc.perform(post("/articles/1/answers")
            .session(session)
            .param("comment", "comment")
            .accept(MediaType.APPLICATION_JSON));

        // then
        actions.andExpect(status().isOk())
            .andExpect(jsonPath("$.replyId", 1).exists())
            .andExpect(jsonPath("$.articleId", 1).exists())
            .andExpect(jsonPath("$.userId", "writer").exists())
            .andExpect(jsonPath("$.comment", "comment").exists());
    }

    @Test
    @DisplayName("세션 정보와 댓글 id 로 댓글을 삭제한다")
    public void deleteAnswerTest() throws Exception {
        // when
        ResultActions actions = mockMvc.perform(delete("/articles/1/answers/1")
            .session(session)
            .accept(MediaType.APPLICATION_JSON));

        // then
        actions.andExpect(status().isOk())
            .andExpect(jsonPath("$.valid", true).exists())
            .andExpect(jsonPath("$.message", "ok").exists());
    }

    @Test
    @DisplayName("세션 정보와 존재하지 않는 댓글 id 로 댓글을 삭제하면 알림창을 띄운다")
    public void deleteAnswerNotFoundTest() throws Exception {
        // given
        doThrow(new NotFoundException(ErrorCode.REPLY_NOT_FOUND))
            .when(replyService).deleteReply(sessionUser, 1);

        // when
        ResultActions actions = mockMvc.perform(
            delete("/articles/1/answers/1")
                .session(session)
                .accept(MediaType.APPLICATION_JSON));

        // then
        actions.andExpect(status().isOk())
            .andExpect(jsonPath("$.valid", true).exists())
            .andExpect(jsonPath("$.message", "등록되지 않은 댓글입니다.").exists());
    }

    @Test
    @DisplayName("세션 정보와 일치하지 않는 유저가 작성한 댓글 id 로 댓글을 삭제하면 알림창을 띄운다")
    public void deleteAnswerValidateTest() throws Exception {
        // given
        SessionUser sessionOther = new SessionUser(1, "otherId", "otherPassword", "otherName",
            "other@example.com");

        session.setAttribute(SessionUser.SESSION_KEY, sessionOther);

        doThrow(new InvalidRequestException(ErrorCode.INCORRECT_USER))
            .when(replyService).deleteReply(sessionUser, 1);

        // when
        ResultActions actions = mockMvc.perform(delete("/articles/1/answers/1")
            .session(session)
            .accept(MediaType.APPLICATION_JSON));

        // then
        actions.andExpect(status().isOk())
            .andExpect(jsonPath("$.valid", true).exists())
            .andExpect(jsonPath("$.message", "다른 유저의 댓글을 수정하거나 삭제할 수 없습니다.").exists());
    }
}
