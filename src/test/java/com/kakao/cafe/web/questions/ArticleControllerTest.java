package com.kakao.cafe.web.questions;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.domain.User;
import com.kakao.cafe.service.ArticleService;
import com.kakao.cafe.service.ReplyService;
import com.kakao.cafe.web.validation.ArticleValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ArticleController.class)
class ArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArticleService mockArticleService;

    @MockBean
    private ReplyService replyService;

    @MockBean
    private ArticleValidation articleValidation;

    private Article article1, article2;
    private MockHttpSession mySession;
    private User user;

    @BeforeEach
    void setUp() {
        article1 = new Article("testA", "title", "content", LocalDateTime.now());
        article2 = new Article("testB", "titleB", "contentB", LocalDateTime.now());
        article1.setId(1L);
        article2.setId(2L);
        given(articleValidation.supports(any())).willReturn(true);

        // user
        user = new User("test1", "1234", "test1", "test11111@naver.com");
        user.setId(11L);

        mySession = new MockHttpSession();
    }

    @Test
    @DisplayName("로그인 하지 않은 사용자가 form 요청시 로그인 화면으로 redirection")
    void request_ArticleForm_Interceptor_Test() throws Exception {
        // when
        ResultActions requestThenResult = mockMvc.perform(get("/questions")
                .session(mySession)
                .accept(MediaType.TEXT_HTML_VALUE));

        // then
        requestThenResult.andExpect(status().is3xxRedirection())
                .andExpect(request().sessionAttributeDoesNotExist("SESSIONED_USER"))
                .andExpect(redirectedUrl("/login?redirectURL=/questions"));

    }

    @Test
    @DisplayName("로그인 하고 글을 작성하고 Post 요청시 root로 redirection 되어야 한다.")
    void getFormArticleTest() throws Exception {
        // given
        mySession.setAttribute("SESSIONED_USER", new User());

        // when
        ResultActions requestThenResult = mockMvc.perform(post("/questions")
                .param("writer", "postTest")
                .param("title", "title")
                .param("contents", "content")
                .session(mySession)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .accept(MediaType.TEXT_HTML_VALUE));

        // then
        requestThenResult.andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }

    @Test
    @DisplayName("로그인 한 사용자만 게시물 세부 내용을 볼 수 있다.")
    public void login_Success_User_Can_See_Article() throws Exception {
        // given
        mySession.setAttribute("SESSIONED_USER", new User());
        given(mockArticleService.findArticleById(any())).willReturn(article1);

        // when
        ResultActions requestThenResult = mockMvc.perform(get("/questions/" + article1.getId())
                .session(mySession)
                .accept(MediaType.TEXT_HTML_VALUE));

        // then
        requestThenResult.andExpect(status().isOk())
                .andExpect(model().attribute("article", article1))
                .andExpect(view().name("/qna/show"));
    }

    @Test
    @DisplayName("로그인 한 사용자만 자신의 게시물을 삭제할 수 있다.")
    public void login_Success_User_Can_Delete_Owner_Article() throws Exception {
        // given : testA 유저 준비
        mySession.setAttribute("SESSIONED_USER", new User("testA", "1234", "test1", "test11111@naver.com"));
        given(mockArticleService.findArticleById(any())).willReturn(article1);
        given(mockArticleService.deleteArticle(any(), any())).willReturn(1L);

        // when : 삭제할 글도 testA 꺼
        ResultActions requestThenResult = mockMvc.perform(post("/questions/" + article1.getId() + "/delete")
                .session(mySession)
                .accept(MediaType.TEXT_HTML_VALUE)
        );

        // then
        requestThenResult.andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    @DisplayName("로그인 한 사용자는 남의 게시물을 삭제할 수 없다.")
    public void login_Success_User_Can_Delete_Other_Article() throws Exception {
        // given
        mySession.setAttribute("SESSIONED_USER", user); // 세션에 저장된 test1 유저
        given(mockArticleService.findArticleById(any())).willReturn(article1);
        given(mockArticleService.deleteArticle(any(), any())).willReturn(1L);

        // when 아티클1 작성 유저는 testA
        ResultActions requestThenResult = mockMvc.perform(post("/questions/" + article1.getId() + "/delete")
                .session(mySession)
                .accept(MediaType.TEXT_HTML_VALUE)
        );

        // then
        requestThenResult.andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }
}
