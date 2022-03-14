package com.kakao.cafe.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = FormController.class)
class FormControllerTest {

    @Autowired
    private MockMvc mvc;

    @DisplayName("/form으로 GET 요청이 오면 /user/form view가 반환된다.")
    @Test
    void get_form() throws Exception {
        mvc.perform(get("/form"))
            .andExpect(status().isOk())
            .andExpect(view().name("user/form"));
    }

    @DisplayName("/qnaForm으로 GET 요청이 오면 /qna/formQna view가 반환된다.")
    @Test
    void get_qnaForm() throws Exception {
        mvc.perform(get("/qnaForm"))
            .andExpect(status().isOk())
            .andExpect(view().name("qna/formQna"));
    }
}
