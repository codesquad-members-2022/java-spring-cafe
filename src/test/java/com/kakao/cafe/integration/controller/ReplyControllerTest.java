package com.kakao.cafe.integration.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.domain.Reply;
import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.ArticleRepository;
import com.kakao.cafe.repository.ReplyRepository;
import com.kakao.cafe.repository.UserRepository;
import com.kakao.cafe.session.SessionUser;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.servlet.HandlerInterceptor;

@SpringBootTest
@ActiveProfiles(profiles = "local")
@ComponentScan
@AutoConfigureMockMvc
@Sql("classpath:/schema-h2.sql")
@DisplayName("ReplyController 통합 테스트")
public class ReplyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReplySetUp replySetUp;

    @MockBean
    private HandlerInterceptor interceptor;
    private MockHttpSession session;
    private Article article;
    private User user;
    private SessionUser sessionUser;
    private SessionUser sessionOther;

    @BeforeEach
    public void setUp() throws Exception {
        given(interceptor.preHandle(any(), any(), any())).willReturn(true);

        article = new Article("writer", "title", "contents");
        user = new User("writer", "userPassword", "userName", "user@example.com");

        sessionUser = new SessionUser(1, "writer", "userPassword", "userName",
            "user@example.com");
        sessionOther = new SessionUser(1, "otherId", "otherPassword", "otherName",
            "other@example.com");

        session = new MockHttpSession();
        session.setAttribute(SessionUser.SESSION_KEY, sessionUser);
    }

    @Test
    @DisplayName("댓글을 작성하고 저장한다")
    public void createAnswerTest() throws Exception {
        // given
        replySetUp.saveArticle(article);
        replySetUp.saveUser(user);

        // when
        ResultActions actions = mockMvc.perform(
            post("/articles/" + article.getArticleId() + "/answers")
                .session(session)
                .param("comment", "comment")
                .accept(MediaType.APPLICATION_JSON));

        // then
        actions.andExpect(status().isOk())
            .andExpect(jsonPath("$.replyId", 1).exists())
            .andExpect(jsonPath("$.articleId", 1).exists())
            .andExpect(jsonPath("$.userId", "writer").exists())
            .andExpect(jsonPath("$.comment", "comment").exists());
    }

    @Test
    @DisplayName("세션 정보와 댓글 id 로 댓글을 삭제한다")
    public void deleteAnswerTest() throws Exception {
        // given
        Article savedArticle = replySetUp.saveArticle(article);
        User savedUser = replySetUp.saveUser(user);
        Reply savedReply = replySetUp.saveReply(
            new Reply(savedArticle.getArticleId(), savedUser.getUserId(), "comment"));

        // when
        ResultActions actions = mockMvc.perform(
            delete("/articles/" + article.getArticleId() + "/answers/" + savedReply.getReplyId())
                .session(session)
                .accept(MediaType.APPLICATION_JSON));

        // then
        actions.andExpect(status().isOk())
            .andExpect(jsonPath("$.valid", true).exists())
            .andExpect(jsonPath("$.message", "ok").exists());
    }

    @Test
    @DisplayName("세션 정보와 존재하지 않는 댓글 id 로 댓글을 삭제하면 알림창을 띄운다")
    public void deleteAnswerNotFoundTest() throws Exception {
        // when
        ResultActions actions = mockMvc.perform(
            delete("/articles/" + article.getArticleId() + "/answers/0")
                .session(session)
                .accept(MediaType.APPLICATION_JSON));

        // then
        actions.andExpect(status().isOk())
            .andExpect(jsonPath("$.valid", true).exists())
            .andExpect(jsonPath("$.message", "등록되지 않은 댓글입니다.").exists());
    }

    @Test
    @DisplayName("세션 정보와 일치하지 않는 유저가 작성한 댓글 id 로 댓글을 삭제하면 알림창을 띄운다")
    public void deleteAnswerValidateTest() throws Exception {
        // given
        Article savedArticle = replySetUp.saveArticle(article);
        User savedUser = replySetUp.saveUser(user);
        Reply savedReply = replySetUp.saveReply(
            new Reply(savedArticle.getArticleId(), savedUser.getUserId(), "comment"));

        session.setAttribute(SessionUser.SESSION_KEY, sessionOther);

        // when
        ResultActions actions = mockMvc.perform(
            delete("/articles/" + article.getArticleId() + "/answers/" + savedReply.getReplyId())
                .session(session)
                .accept(MediaType.APPLICATION_JSON));

        // then
        actions.andExpect(status().isOk())
            .andExpect(jsonPath("$.valid", true).exists())
            .andExpect(jsonPath("$.message", "다른 유저의 댓글을 수정하거나 삭제할 수 없습니다.").exists());
    }

    @Component
    public static class ReplySetUp {

        private final ArticleRepository articleRepository;
        private final UserRepository userRepository;
        private final ReplyRepository replyRepository;

        public ReplySetUp(ArticleRepository articleRepository, UserRepository userRepository,
            ReplyRepository replyRepository) {
            this.articleRepository = articleRepository;
            this.userRepository = userRepository;
            this.replyRepository = replyRepository;
        }

        public User saveUser(User user) {
            return userRepository.save(user);
        }

        public Article saveArticle(Article article) {
            return articleRepository.save(article);
        }

        public Reply saveReply(Reply reply) {
            return replyRepository.save(reply);
        }

    }

}
