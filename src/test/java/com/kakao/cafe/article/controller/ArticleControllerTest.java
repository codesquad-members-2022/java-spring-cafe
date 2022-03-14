package com.kakao.cafe.article.controller;

import com.kakao.cafe.article.domain.Article;
import com.kakao.cafe.article.repository.ArticleRepository;
import com.kakao.cafe.article.repository.MemoryArticleRepository;
import com.kakao.cafe.article.service.ArticleService;
import com.kakao.cafe.exception.domain.RequiredFieldNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ArticleController 단위 테스트")
class ArticleControllerTest {

    @Mock
    private ArticleService articleService;

    @InjectMocks
    private ArticleController articleController;

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
                String questionWriteView = "qna/form";
                when(articleService.write(any())).thenThrow(RequiredFieldNotFoundException.class);

                // act
                ResultActions result = mockMvc.perform(
                        MockMvcRequestBuilders.post("/questions")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("content", "반갑습니다. 본문입니다.")
                );

                // assert
                result.andExpect(status().is4xxClientError())
                        .andExpect(forwardedUrl(questionWriteView));
            }
        }
    }

    @Nested
    @DisplayName("글 목록 조회는")
    class MainPageTest {

        @Nested
        @DisplayName("저장된 게시글이 있으면")
        class ArticleExistTest {

            @Test
            void 게시글_목록과_함께_메인_페이지로_이동한다() throws Exception {
                // arrange
                String mainPageViewName = "index";
                String articlesKey = "articles";
                String articleCountKey = "articleCount";
                Article articleMock = new Article("", null, null, null);
                when(articleService.findArticles()).thenReturn(List.of(articleMock));

                // act
                ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/"));

                // assert
                ModelAndView modelAndView = resultActions.andExpect(status().isOk())
                        .andReturn()
                        .getModelAndView();
                assertThat(modelAndView).isNotNull();
                assertThat(modelAndView.getViewName()).isEqualTo(mainPageViewName);
                assertThat(modelAndView.getModel()).isNotNull();
                assertThat(modelAndView.getModel().containsKey(articlesKey)).isTrue();
                assertThat(modelAndView.getModel().containsKey(articleCountKey)).isTrue();
            }
        }

        @Nested
        @DisplayName("저장된 게시글이 없어도")
        class NoArticleTest {

            @Test
            void 메인_페이지로_이동한다() throws Exception {
                // arrange
                String mainPageViewName = "index";
                when(articleService.findArticles()).thenReturn(Collections.emptyList());

                // act
                ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/"));

                // assert
                ModelAndView modelAndView = resultActions.andExpect(status().isOk())
                        .andReturn()
                        .getModelAndView();
                assertThat(modelAndView).isNotNull();
                assertThat(modelAndView.getViewName()).isEqualTo(mainPageViewName);
            }
        }
    }
}