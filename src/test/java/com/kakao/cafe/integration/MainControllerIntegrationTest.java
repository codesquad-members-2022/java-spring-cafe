package com.kakao.cafe.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.service.ArticleService;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
public class MainControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ArticleService articleService;

    @AfterEach
    void close() {
        articleService.deleteAll();
    }

    @DisplayName("사용자가 메인 화면(/)을 요청하면 모든 게시글 정보를 model에 저장하고 /index view를 반환한다.")
    @Test
    void 모든_게시글_정보_조회() throws Exception {
        // given
        Article article1 = new Article("ikjo", "java", "java is fun");
        Article article2 = new Article("ikjo", "python", "python is fun");
        articleService.upload(article1);
        articleService.upload(article2);

        // when
        ResultActions resultActions = mockMvc.perform(get("/"));

        // then
        resultActions.andExpect(status().isOk())
            .andExpect(view().name("/index"))
            .andExpect(model().attribute("articles", List.of(article1, article2)));
    }
}
