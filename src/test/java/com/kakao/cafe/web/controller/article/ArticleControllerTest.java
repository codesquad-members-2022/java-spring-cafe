package com.kakao.cafe.web.controller.article;

import com.kakao.cafe.core.domain.article.Article;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ArticleController.class)
@DisplayName("ArticleController테스트")
class ArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    ArticleController articleController;

    @MockBean
    private ArticleService articleService;

    Article article;

    @BeforeEach
    void init() {
        article = new Article.Builder()
                .id(1)
                .title("title")
                .content("content")
                .writer("writer")
                .createAt(LocalDateTime.now())
                .lastModifiedAt(LocalDateTime.now())
                .viewCount(0)
                .build();
    }

    @Test
    @DisplayName("/articles/write로 이동하면 article/write view를 반환한다.")
    void write_view_반환() throws Exception {
        mockMvc.perform(get("/articles/write"))
                .andExpect(status().isOk())
                .andExpect(view().name("article/write"));
    }

    @Test
    @DisplayName("/articles 이동하면 article/list view를 반환한다.")
    void list_view_반환() throws Exception {
        mockMvc.perform(get("/articles"))
                .andExpect(status().isOk())
                .andExpect(view().name("article/list"));
    }

    @Test
    @DisplayName("/articles/{id} 이동하면 article/detail view를 반환한다.")
    void post_detail_view_반환() throws Exception {

        given(articleService.findById(anyInt())).willReturn(article);

        mockMvc.perform(get("/articles/" + anyInt()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("findArticle"))
                .andExpect(model().attribute("findArticle", article))
                .andExpect(view().name("article/detail"));
    }

    @Test
    @DisplayName("모든 게시글을 조회회하면 article/list로 이동하게 된다.")
    void 전체_게시글_조회_페이지_이동() throws Exception {

        List<Article> articles = List.of();

        mockMvc.perform(get("/articles"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("articles"))
                .andExpect(model().attribute("articles", articles))
                .andExpect(view().name("article/list"));
    }

    @Test
    @DisplayName("등록된 모든 글을 화면에 출력한다")
    public void listArticlesTest() throws Exception {
        // given
//        Article savedArticle = articleSetUp.saveArticle(article);

        // when
//        ResultActions actions = performGet("/");

        // then
//        actions.andExpect(status().isOk())
//                .andExpect(model().attribute("articles", List.of(savedArticle)))
//                .andExpect(view().name("qna/list"));
    }

    @Test
    @DisplayName("글을 작성하는 화면을 보여준다")
    public void createQuestionTest() throws Exception {
//        // when
//        ResultActions actions = mockMvc.perform(
//                get("/articles")
//                        .accept(MediaType.TEXT_HTML)
//                                .

        // then
//        actions.andExpect(status().isOk())
//                .andExpect(view().name("qna/form"));
    }


}