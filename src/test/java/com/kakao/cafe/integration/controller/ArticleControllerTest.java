package com.kakao.cafe.integration.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.domain.User;
import com.kakao.cafe.dto.ArticleResponse;
import com.kakao.cafe.dto.UserResponse;
import com.kakao.cafe.exception.ErrorCode;
import com.kakao.cafe.repository.ArticleRepository;
import com.kakao.cafe.repository.UserRepository;
import com.kakao.cafe.util.SessionUtil;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.stereotype.Component;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.servlet.HandlerInterceptor;

@SpringBootTest
@ComponentScan
@AutoConfigureMockMvc
@Sql("classpath:/schema.sql")
@DisplayName("ArticleController 통합 테스트")
public class ArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ArticleSetUp articleSetUp;

    @MockBean
    private HandlerInterceptor interceptor;

    private MockHttpSession session;

    private Article article;
    private ArticleResponse articleResponse;
    private UserResponse userResponse;

    @Component
    public static class ArticleSetUp {

        private final ArticleRepository articleRepository;
        private final UserRepository userRepository;

        public ArticleSetUp(ArticleRepository articleRepository, UserRepository userRepository) {
            this.articleRepository = articleRepository;
            this.userRepository = userRepository;
        }

        public User saveUser(User user) {
            return userRepository.save(user);
        }

        public Article saveArticle(Article article) {
            return articleRepository.save(article);
        }

        public void rollback() {
            articleRepository.deleteAll();
        }
    }

    @BeforeEach
    public void setUp() throws Exception {
        given(interceptor.preHandle(any(), any(), any())).willReturn(true);

        article = new Article("writer", "title", "contents");
        articleResponse = new ArticleResponse(1, "writer", "title", "contents", null);
        userResponse = new UserResponse(1, "writer", "userPassword", "userName",
            "user@example.com");

        session = new MockHttpSession();
        session.setAttribute(SessionUtil.SESSION_USER, userResponse);
    }

    @AfterEach
    public void exit() {
        articleSetUp.rollback();
    }

    private ResultActions performGet(String url) throws Exception {
        return mockMvc.perform(
            get(url).accept(MediaType.TEXT_HTML)
        );
    }

    @Test
    @DisplayName("글을 작성하는 화면을 보여준다")
    public void createQuestionTest() throws Exception {
        // when
        ResultActions actions = performGet("/questions");

        // then
        actions.andExpect(status().isOk())
            .andExpect(view().name("qna/form"));
    }

    @Test
    @DisplayName("글을 작성하고 업로드한다")
    public void createTest() throws Exception {
        // given
        articleSetUp.saveUser(new User("writer", "userPassword", "userName", "user@example.com"));

        // when
        ResultActions actions = mockMvc.perform(post("/questions")
            .session(session)
            .param("writer", "writer")
            .param("title", "title")
            .param("contents", "contents")
            .accept(MediaType.TEXT_HTML));

        // then
        actions.andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/"));
    }

    @Test
    @DisplayName("등록된 모든 글을 화면에 출력한다")
    public void listArticlesTest() throws Exception {
        // given
        articleSetUp.saveArticle(article);

        // when
        ResultActions actions = performGet("/");

        // then
        actions.andExpect(status().isOk())
            .andExpect(model().attribute("articles", List.of(articleResponse)))
            .andExpect(view().name("qna/list"));
    }

    @Test
    @DisplayName("질문 id 로 선택한 질문을 화면에 출력한다")
    public void showArticleTest() throws Exception {
        // given
        Article savedArticle = articleSetUp.saveArticle(article);

        // when
        ResultActions actions = performGet("/articles/" + savedArticle.getArticleId());

        // then
        actions.andExpect(status().isOk())
            .andExpect(model().attribute("article", articleResponse))
            .andExpect(view().name("qna/show"));
    }

    @Test
    @DisplayName("존재하지 않은 질문 id 로 질문을 조회하면 예외 페이지로 이동한다")
    public void showArticleValidateTest() throws Exception {
        // when
        ResultActions actions = performGet("/articles/0");

        // then
        actions.andExpect(status().isOk())
            .andExpect(model().attribute("status", ErrorCode.ARTICLE_NOT_FOUND.getHttpStatus()))
            .andExpect(model().attribute("message", ErrorCode.ARTICLE_NOT_FOUND.getMessage()))
            .andExpect(view().name("error/index"));
    }

}
