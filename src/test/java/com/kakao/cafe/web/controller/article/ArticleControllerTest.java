package com.kakao.cafe.web.controller.article;

import com.kakao.cafe.core.domain.article.Article;
import com.kakao.cafe.web.controller.article.dto.ArticleResponse;
import com.kakao.cafe.web.controller.article.dto.ArticleWriteRequest;
import com.kakao.cafe.web.service.article.ArticleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ArticleController.class)
@DisplayName("ArticleController 단위 테스트")
class ArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    BindingResult bindingResult;

    @MockBean
    private ArticleService articleService;

    Article article;
    ArticleWriteRequest articleWriteRequest;
    ArticleResponse articleResponse;

    @BeforeEach
    void init() {
        article = getArticle();
        articleWriteRequest = getArticleWriteRequest();
        bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);
        articleResponse = getArticleResponse();
    }

    private Article getArticle() {
        return new Article.Builder()
                .id(getFixedId())
                .title(getTitle())
                .content(getContent())
                .writer(getWriter())
                .createAt(getNow())
                .lastModifiedAt(getNow())
                .viewCount(getFixedViewCount())
                .build();
    }

    private ArticleResponse getArticleResponse() {
        return new ArticleResponse(getArticle());
    }



    @Test
    @DisplayName("/articles/write로 이동하면 article/write view를 반환한다.")
    void write_view_반환_get() throws Exception {
        mockMvc.perform(get("/articles/write"))
                .andExpect(status().isOk())
                .andExpect(view().name("article/write"));
    }

    @Test
    @DisplayName("/articles/write로 이동하면 article/write view를 반환한다.")
    void write_view_반환_post() throws Exception {
        mockMvc.perform(post("/articles/write"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/articles"));
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

        given(articleService.findById(anyInt())).willReturn(getArticle());

        mockMvc.perform(get("/articles/" + anyInt()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("findArticle"))
                .andExpect(model().attribute("findArticle", getArticleResponse()))
                .andExpect(view().name("article/detail"));
    }

    @Test
    @DisplayName("모든 게시글을 조회회하면 article/list로 이동하게 된다.")
    void 전체_게시글_조회_페이지_이동() throws Exception {

        List<ArticleResponse> articles = List.of();

        mockMvc.perform(get("/articles"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("articles"))
                .andExpect(model().attribute("articles", articles))
                .andExpect(view().name("article/list"));
    }

    private String getTitle() {
        return "title";
    }

    private String getContent() {
        return "content";
    }

    private String getWriter() {
        return "writer";
    }

    private ArticleWriteRequest getArticleWriteRequest() {
        return new ArticleWriteRequest("title", "content", "writer");
    }

    private int getFixedId() {
        return 1;
    }

    private int getFixedViewCount() {
        return 0;
    }

    private LocalDateTime getNow() {
        return LocalDateTime.now();
    }
}