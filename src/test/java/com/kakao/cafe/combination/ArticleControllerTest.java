package com.kakao.cafe.combination;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.kakao.cafe.dto.UserArticle;
import com.kakao.cafe.service.ArticleService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@SpringBootTest
@AutoConfigureMockMvc
public class ArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ArticleService articleService;

    @AfterEach
    void close() {
        articleService.deleteAll();
    }

    @DisplayName("사용자가 게시글 저장을 요청하면 게시글 정보를 저장하고 /로 리다이렉트한다.")
    @Test
    void 게시글_저장() throws Exception {
        // given
        MultiValueMap<String, String> userArticleParams = new LinkedMultiValueMap<>();
        userArticleParams.add("writer", "ikjo");
        userArticleParams.add("title", "java");
        userArticleParams.add("contents", "java is fun");

        // when
        ResultActions resultActions = mockMvc.perform(post("/questions").params(userArticleParams));

        // then
        resultActions.andExpect(redirectedUrl("/"));
    }

    @DisplayName("사용자가 특정 게시글 정보를 요청하면 model과 /qna/show view를 반환한다.")
    @Test
    void 특정_게시글_조회() throws Exception {
        // given
        UserArticle userArticle = new UserArticle("ikjo", "java", "java is fun");
        articleService.upload(userArticle);

        // when
        ResultActions resultActions = mockMvc.perform(get("/articles/1"));

        // then
        resultActions.andExpect(status().isOk())
            .andExpect(view().name("/qna/show"))
            .andExpect(model().attribute("article", userArticle));
    }
}
