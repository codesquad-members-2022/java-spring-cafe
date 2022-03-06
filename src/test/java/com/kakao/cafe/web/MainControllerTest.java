package com.kakao.cafe.web;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.service.ArticleService;
import com.kakao.cafe.web.questions.ArticleController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MainController.class)
class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArticleService mockArticleService;

    private Article article1, article2;

    @BeforeEach
    void setUp() {
        article1 = new Article("testA", "title", "content", LocalDateTime.now());
        article2 = new Article("testB", "titleB", "contentB", LocalDateTime.now());
        article1.setId(1L);
        article2.setId(2L);
    }

    @Test
    @DisplayName("기사의 리스트 출력 확인하기")
    void articleListTest() throws Exception {
        // given
        Map<Long, Article> articleMap = new ConcurrentHashMap<>();
        articleMap.put(1L, article1);
        articleMap.put(2L, article2);

        given(mockArticleService.findArticles()).willReturn(new ArrayList<>(articleMap.values()));

        // when
        ResultActions requestThenResult = mockMvc.perform(get("/")
                .accept(MediaType.TEXT_HTML_VALUE));

        // then
        requestThenResult.andExpect(status().isOk())
                .andExpect(model().attribute("articles", new ArrayList<>(articleMap.values())))
                .andExpect(view().name("index"));
    }

}
