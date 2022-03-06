package com.kakao.cafe.web;

import com.kakao.cafe.domain.article.Article;
import com.kakao.cafe.service.ArticleService;
import com.kakao.cafe.web.dto.ArticleResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;


import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ArticleController.class)
class ArticleControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ArticleService articleService;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("/qna/write-qna로 get 요청시 /qna/form 호출(게시글 작성폼으로 이동한다.)")
    void writeForm() throws Exception {

        mockMvc.perform(get("/qna/write-qna"))
                .andExpect(status().isOk())
                .andExpect(view().name("/qna/form"));
    }

    @Test
    @DisplayName("/qna/write-qna로 post 요청(ArticleDto)시 /qna/all 리다이렉트 된다.(게시글 목록을 보여준다.)")
    void write() throws Exception {

        mockMvc.perform(post("/qna/write-qna")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .content("writer=작성자&title=제목&contents=본문"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/qna/all"));
    }

    @Test
    @DisplayName("/qna/all로 get 요청시 /qna/list 호출해서 게시글 목록을 보여준다.")
    void showAll() throws Exception {

        Article article = new Article("작성자","제목","본문");
        List<ArticleResponseDto> articleResponseDtos = List.of(new ArticleResponseDto(article));

        given(articleService.findAll()).willReturn(articleResponseDtos);

        mockMvc.perform(get("/qna/all"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("articles"))
                .andExpect(model().attribute("articles", articleResponseDtos))
                .andExpect(view().name("/qna/list"));
    }

    @Test
    @DisplayName("/qna/show/{id} get 요청시 해당 id를 가지고 있는 게시글을 /qna/show에서 보여준다.")
    void showArticle() throws Exception {
        Article article = new Article("작성자","제목","본문");
        ArticleResponseDto articleResponseDto = new ArticleResponseDto(article);

        given(articleService.findOne(anyInt())).willReturn(articleResponseDto);

        mockMvc.perform(get("/qna/show/"+anyInt()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("article"))
                .andExpect(model().attribute("article", articleResponseDto))
                .andExpect(view().name("/qna/show"));
    }
}
