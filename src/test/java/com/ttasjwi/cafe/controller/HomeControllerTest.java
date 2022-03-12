package com.ttasjwi.cafe.controller;

import com.ttasjwi.cafe.repository.ArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(controllers = HomeController.class)
@ExtendWith(SpringExtension.class)
class HomeControllerTest {

    @Autowired
    private MockMvc mvc; // mockMvc

    @MockBean
    private ArticleRepository articleRepository;

    @Test
    @DisplayName("루트 경로에 요청하면 \"home\"이 return됨")
    void home() throws Exception {
        String homeUrl = "/";

        mvc.perform(get(homeUrl))
                .andExpect(status().isOk())
                .andExpect(view().name("home"));
    }
}