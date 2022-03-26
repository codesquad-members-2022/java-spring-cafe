package com.kakao.cafe.web;

import com.kakao.cafe.constants.LoginConstants;
import com.kakao.cafe.domain.article.Article;
import com.kakao.cafe.domain.user.User;
import com.kakao.cafe.service.ArticleService;
import com.kakao.cafe.web.dto.ArticleResponseDto;
import com.kakao.cafe.web.dto.UserResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ArticleController.class)
class ArticleControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ArticleService articleService;

    private MockHttpSession httpSession = new MockHttpSession();

    @BeforeEach
    void SetUp() {
        User user = new User("ron2", "1234", "로니", "ron2@gmail.com");
        httpSession.setAttribute(LoginConstants.SESSIONED_USER, new UserResponseDto(user));
    }

    @Test
    @DisplayName("/qna/write-qna로 get 요청시 로그인 상태라면 /qna/form 호출(게시글 작성폼으로 이동한다.)")
    void writeFormTest() throws Exception {

        mockMvc.perform(get("/qna/write-qna").session(httpSession))
                .andExpect(status().isOk())
                .andExpect(view().name("qna/form"));
    }

    @Test
    @DisplayName("/qna/write-qna로 get 요청시 로그인을 하지 않았다면, 인터셉터를 거쳐서 /user/login으로 리다이렉션 된다.")
    void writeForm_not_logined_user_redirect_test() throws Exception {

        mockMvc.perform(get("/qna/write-qna"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/login"));
    }

    @Test
    @DisplayName("/qna/write-qna로 post 요청(ArticleDto)시 /qna/all 리다이렉트 된다.(게시글 목록을 보여준다.)")
    void writeTest() throws Exception {

        mockMvc.perform(post("/qna/write-qna")
                    .session(httpSession)
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .content("title=제목&contents=본문"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/qna/all"));
    }

    @Test
    @DisplayName("/qna/write-qna로 post 요청(ArticleDto)시 로그인이 되어있지 않다면, 인터셉터를 거쳐서 /user/login으로 리다이렉션된다. ")
    void write_not_logined_user_redirect_test() throws Exception {

        mockMvc.perform(post("/qna/write-qna")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content("title=제목&contents=본문"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/login"));
    }

    @Test
    @DisplayName("/qna/all로 get 요청시 /qna/list 호출해서 게시글 목록을 보여준다.")
    void showAll() throws Exception {

        Article article = Article.newInstance(1,"작성자","제목","본문");
        List<ArticleResponseDto> articleResponseDtos = List.of(new ArticleResponseDto(article));

        given(articleService.findAll()).willReturn(articleResponseDtos);

        mockMvc.perform(get("/qna/all"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("articles"))
                .andExpect(model().attribute("articles", articleResponseDtos))
                .andExpect(view().name("qna/list"));
    }

    @Test
    @DisplayName("/qna/show/{id} get 요청시 해당 id를 가지고 있는 게시글을 /qna/show에서 보여준다.")
    void showArticle() throws Exception {
        Article article = Article.newInstance(1,"작성자","제목","본문");
        ArticleResponseDto articleResponseDto = new ArticleResponseDto(article);

        given(articleService.findOne(anyInt())).willReturn(articleResponseDto);

        mockMvc.perform(get("/qna/show/"+anyInt()).session(httpSession))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("article"))
                .andExpect(model().attribute("article", articleResponseDto))
                .andExpect(view().name("qna/show"));
    }

    @Test
    @DisplayName("/qna/delete/{id} DELETE 요청시 로그인된 해당유저가 작성한 글이면 삭제하고, /qna/all로 리다이렉션한다.")
    void deleteArticleTest() throws Exception {
        //given
        Integer articleId = 1;
        String writer = "ron2";
        Article article = Article.newInstance(articleId, writer, "제목","본문");
        given(articleService.findOne(any())).willReturn(new ArticleResponseDto(article));

        //when
        mockMvc.perform(delete("/qna/delete/"+articleId)
                        .session(httpSession))
                //then
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/qna/all"));

        verify(articleService, only()).deleteOne(articleId, writer);

    }

    @Test
    @DisplayName("/qna/update/{id} get 요청 시 qna/update_fom을 리턴한다.")
    void updateFormTest() throws Exception {
        //given
        Integer articleId = 1;
        Article article = Article.newInstance(articleId, "ron2", "제목","본문");
        ArticleResponseDto articleResponseDto = new ArticleResponseDto(article);
        given(articleService.findOne(any())).willReturn(articleResponseDto);

        //when
        mockMvc.perform(get("/qna/update/"+articleId).session(httpSession))
                //then
                .andExpect(status().isOk())
                .andExpect(model().attribute("article", articleResponseDto))
                .andExpect(view().name("qna/update_form"));
    }

    @Test
    @DisplayName("/qna/update/{id} put 요청시 게시글을 수정 후 /qna/show/{id}로 리다이렉션한다.")
    void updateArticle() throws Exception {
        Integer articleId = 1;

        //when
        mockMvc.perform(put("/qna/update/"+articleId)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content("title=제목&contents=본문")
                .session(httpSession))
                //then
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/qna/show/"+articleId));
    }
}
