package com.kakao.cafe.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.kakao.cafe.service.ArticleService;
import com.kakao.cafe.service.UserService;

@WebMvcTest(controllers = ArticleController.class)
class ArticleControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ArticleService articleService;

    @DisplayName("/questions로 POST 요청을 하면 질문이 저장되고 /로 리다이렉트 된다.")
    @Test
    void post_questions() throws Exception {
        mvc.perform(post("/questions"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/"));
    }
}