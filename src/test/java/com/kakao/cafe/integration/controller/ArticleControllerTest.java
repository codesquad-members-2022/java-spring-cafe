package com.kakao.cafe.integration.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.domain.User;
import com.kakao.cafe.exception.ErrorCode;
import com.kakao.cafe.repository.ArticleRepository;
import com.kakao.cafe.repository.UserRepository;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@ComponentScan
@AutoConfigureMockMvc
@Sql("classpath:/schema.sql")
@DisplayName("ArticleController 통합 테스트")
public class ArticleControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ArticleSetUp articleSetUp;

    Article article;

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
    public void setUp() {
        article = new Article.Builder()
            .writer("writer")
            .title("title")
            .contents("contents")
            .build();
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
        articleSetUp.saveUser(
            new User.Builder()
                .userId("writer")
                .password("userPassword")
                .name("userName")
                .email("user@example.com")
                .build()
        );

        // when
        ResultActions actions = mockMvc.perform(post("/questions")
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
        Article savedArticle = articleSetUp.saveArticle(article);

        // when
        ResultActions actions = performGet("/");

        // then
        actions.andExpect(status().isOk())
            .andExpect(model().attribute("articles", List.of(savedArticle)))
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
            .andExpect(model().attribute("article", savedArticle))
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
