package com.kakao.cafe.article.controller;

import com.kakao.cafe.article.repository.MemoryArticleRepository;
import com.kakao.cafe.article.service.ArticleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("ArticleController 단위 테스트")
class ArticleControllerTest {

    private final ArticleService articleService = new ArticleService(new MemoryArticleRepository());
    private final ArticleController articleController = new ArticleController(articleService);

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(articleController).build();
    }

    @Nested
    @DisplayName("질문 글쓰기는")
    class QuestionWriteTest {

        @Nested
        @DisplayName("정보가 정상적으로 들어왔을 경우")
        class CorrectDataTest {
            @Test
            void 메인페이지로_이동한다() throws Exception {
                // arrange
                String applicationRootUrlPath = "/";

                // act
                ResultActions result = mockMvc.perform(
                        MockMvcRequestBuilders.post("/questions")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("title", "안녕하세요 제목입니다.")
                                .param("content", "반갑습니다. 본문입니다.")
                );

                // assert
                result.andExpect(status().is3xxRedirection())
                        .andExpect(redirectedUrl(applicationRootUrlPath));
            }
        }

        @Nested
        @DisplayName("제목을 입력하지 않으면")
        class TitleEmptyTest {
            @Test
            void 질문_글쓰기_화면으로_돌아간다() throws Exception {
                // arrange
                String questionWriteUrlPath = "qna/form";

                // act
                ResultActions result = mockMvc.perform(
                        MockMvcRequestBuilders.post("/questions")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("content", "반갑습니다. 본문입니다.")
                );

                // assert
                result.andExpect(status().is4xxClientError())
                        .andExpect(forwardedUrl(questionWriteUrlPath));
            }
        }


    }

}