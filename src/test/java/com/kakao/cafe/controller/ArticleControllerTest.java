package com.kakao.cafe.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ArticleControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void 만약_클라이언트가_questions로_접속하면_글작성화면을_보여준다() throws Exception {
        ResultActions actions = mockMvc.perform(get("/questions")
                .accept(MediaType.TEXT_HTML));

        actions.andExpect(status().isOk())
                .andExpect(view().name("qna/form"));
    }

    @Test
    void 만약_클라이언트가_qna_작성하고_질문하기버튼을누르면_글이_홈으로_리다이렉트된다() throws Exception {

        ResultActions actions = mockMvc.perform(post("/questions")
                .param("writer", "쿠킴")
                .param("title", "제목1234")
                .param("text", "본문1234")
                .accept(MediaType.TEXT_HTML));

        actions.andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }
}
