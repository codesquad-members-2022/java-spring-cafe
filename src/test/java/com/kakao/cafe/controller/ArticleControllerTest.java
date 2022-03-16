package com.kakao.cafe.controller;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.domain.ArticleWriteRequest;
import com.kakao.cafe.service.ArticleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ArticleController.class)
@AutoConfigureMockMvc
public class ArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArticleService articleService;

    private Article article;

    @BeforeEach
    void setUp() {
        article = new ArticleWriteRequest("쿠킴1", "제목1", "본문1").toDomain();
        article.setId(1);
    }

    @Test
    void 만약_클라이언트가_questions로_접속하면_글작성화면을_보여준다() throws Exception {
        ResultActions actions = mockMvc.perform(get("/questions")
                .accept(MediaType.TEXT_HTML));

        actions.andExpect(status().isOk())
                .andExpect(view().name("qna/form"));
    }

    @Test
    void 만약_클라이언트가_qna_작성하고_질문하기버튼을누르면_글이_저장되고_홈으로_리다이렉트된다() throws Exception {

        ResultActions actions = mockMvc.perform(post("/questions")
                .param("writer", "쿠킴")
                .param("title", "제목1234")
                .param("text", "본문1234")
                .accept(MediaType.TEXT_HTML));

        actions.andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }

    @Test
    void 만약_qna_글이_하나있고_클라이언트가_루트_URL_접속한다면_qna_리스트를_보여준다() throws Exception {
        given(articleService.findArticles())
                .willReturn(List.of(article));

        ResultActions actions = mockMvc.perform(get("/")
                .accept(MediaType.TEXT_HTML));

        actions.andExpect(status().isOk())
                .andExpect(model().attribute("articles", List.of(article)))
                .andExpect(view().name("index"));
    }

    @Test
    void 만약_특정_qna글의_id_URL로_접속한다면_질문을_화면에_출력한다() throws Exception {
        given(articleService.findArticle(any()))
                .willReturn(article);

        ResultActions actions = mockMvc.perform(get("/articles/" + article.getId())
                .accept(MediaType.TEXT_HTML));

        actions.andExpect(status().isOk())
                .andExpect(model().attribute("article", article))
                .andExpect(view().name("qna/show"));
    }

}
