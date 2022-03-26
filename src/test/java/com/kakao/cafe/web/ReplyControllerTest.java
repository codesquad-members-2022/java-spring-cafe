package com.kakao.cafe.web;

import com.kakao.cafe.constants.LoginConstants;
import com.kakao.cafe.domain.reply.Reply;
import com.kakao.cafe.domain.user.User;
import com.kakao.cafe.service.ReplyService;
import com.kakao.cafe.web.dto.ReplyResponseDto;
import com.kakao.cafe.web.dto.SessionUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReplyController.class)
class ReplyControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ReplyService replyService;

    private MockHttpSession httpSession = new MockHttpSession();

    @BeforeEach
    void setUp() {
        SessionUser sessionUser = SessionUser.from(new User("ron2", "1234", "로니", "ron2@gmail.com"));
        httpSession.setAttribute(LoginConstants.SESSIONED_USER, sessionUser);
    }

    @Test
    @DisplayName("/qna/{articleId}/reply/write post 요청시 contents를 받아서 댓글을 작성하고, /qna/show/{articleId}로 리다이렉션한다.")
    void writeTest() throws Exception {
        //given
        Reply reply = Reply.of(1,1,"ron2","답변", LocalDateTime.now());
        given(replyService.write(1,"ron2","답변")).willReturn(ReplyResponseDto.from(reply));

        //when
        mockMvc.perform(post("/qna/1/reply/write")
                .session(httpSession)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content("contents=답변"))
                //then
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/qna/show/1"));

    }

    @Test
    @DisplayName("/qna/{articleId}/reply/{id}/delete delete 요청시 해당 댓글을 삭제하고 /qna/show/{articleId}로 리다이렉션한다.")
    void deleteTest() throws Exception {

        //when
        mockMvc.perform(delete("/qna/1/reply/1/delete")
                        .session(httpSession))
                //then
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/qna/show/1"));

        verify(replyService, only()).delete("ron2", 1);

    }

}
