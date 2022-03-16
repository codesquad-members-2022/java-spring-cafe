package com.kakao.cafe.integration.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.domain.Reply;
import com.kakao.cafe.domain.User;
import com.kakao.cafe.dto.ArticleResponse;
import com.kakao.cafe.exception.ErrorCode;
import com.kakao.cafe.repository.ArticleRepository;
import com.kakao.cafe.repository.ReplyRepository;
import com.kakao.cafe.repository.UserRepository;
import com.kakao.cafe.session.SessionUser;
import java.time.LocalDateTime;
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

// TODO: ReplyController 통합 테스트와 함께 작성 -> 분리 필요

@SpringBootTest
@ComponentScan
@AutoConfigureMockMvc
@Sql("classpath:/schema-mysql.sql")
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
    private User user;
    private Reply reply;
    private ArticleResponse articleResponse;
    private SessionUser sessionUser;
    private SessionUser sessionOther;

    @BeforeEach
    public void setUp() throws Exception {
        given(interceptor.preHandle(any(), any(), any())).willReturn(true);

        article = Article.createWithInput("writer", "title", "contents");
        user = User.createWithInput("writer", "userPassword", "userName", "user@example.com");

        articleResponse = ArticleResponse.createWithArticle(1, "writer", "title", "contents",
            LocalDateTime.now());
        sessionUser = new SessionUser(1, "writer", "userPassword", "userName",
            "user@example.com");
        sessionOther = new SessionUser(1, "otherId", "otherPassword", "otherName",
            "other@example.com");

        session = new MockHttpSession();
        session.setAttribute(SessionUser.SESSION_KEY, sessionUser);
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
    public void createArticleTest() throws Exception {
        // when
        ResultActions actions = performGet("/articles/form");

        // then
        actions.andExpect(status().isOk())
            .andExpect(view().name("qna/form"));
    }

    @Test
    @DisplayName("글을 작성하고 업로드한다")
    public void createTest() throws Exception {
        // given
        articleSetUp.saveUser(
            User.createWithInput("writer", "userPassword", "userName", "user@example.com"));

        // when
        ResultActions actions = mockMvc.perform(post("/articles")
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

    @Test
    @DisplayName("세션 정보와 질문 id 로 유저의 질문을 조회하고 변경 폼으로 이동한다")
    public void formUpdateArticleTest() throws Exception {
        // given
        Article savedArticle = articleSetUp.saveArticle(article);

        // when
        ResultActions actions = mockMvc.perform(
            get("/articles/" + savedArticle.getArticleId() + "/form")
                .session(session)
                .accept(MediaType.TEXT_HTML));

        // then
        actions.andExpect(status().isOk())
            .andExpect(model().attribute("article", articleResponse))
            .andExpect(view().name("qna/form"));
    }

    @Test
    @DisplayName("세션 정보와 존재하지 않는 질문 id 로 유저의 질문을 조회하면 에러페이지로 이동한다")
    public void formUpdateArticleNotFoundTest() throws Exception {
        // when
        ResultActions actions = mockMvc.perform(get("/articles/0/form")
            .session(session)
            .accept(MediaType.TEXT_HTML));

        // then
        actions.andExpect(status().isOk())
            .andExpect(model().attribute("status", ErrorCode.ARTICLE_NOT_FOUND.getHttpStatus()))
            .andExpect(model().attribute("message", ErrorCode.ARTICLE_NOT_FOUND.getMessage()))
            .andExpect(view().name("error/index"));
    }

    @Test
    @DisplayName("다른 세션 정보와 질문 id 로 유저의 질문을 조회하면 에러페이지로 이동한다")
    public void formUpdateArticleValidateTest() throws Exception {
        // given
        articleSetUp.saveArticle(article);
        session.setAttribute(SessionUser.SESSION_KEY, sessionOther);

        // when
        ResultActions actions = mockMvc.perform(get("/articles/1/form")
            .session(session)
            .accept(MediaType.TEXT_HTML));

        // then
        actions.andExpect(status().isOk())
            .andExpect(
                model().attribute("status", ErrorCode.INVALID_ARTICLE_WRITER.getHttpStatus()))
            .andExpect(model().attribute("message", ErrorCode.INVALID_ARTICLE_WRITER.getMessage()))
            .andExpect(view().name("error/index"));
    }

    @Test
    @DisplayName("세션 정보, 질문 변경 사항과 질문 id 로 유저의 질문을 업데이트하고 첫 페이지로 이동한다")
    public void updateArticleTest() throws Exception {
        // given
        Article savedArticle = articleSetUp.saveArticle(article);

        // when
        ResultActions actions = mockMvc.perform(put("/articles/" + savedArticle.getArticleId())
            .session(session)
            .param("title", "otherTitle")
            .param("contents", "otherContents")
            .accept(MediaType.TEXT_HTML));

        // then
        actions.andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/"));
    }

    @Test
    @DisplayName("세션 정보, 질문 변경 사항과 존재하지 않는 질문 id 로 유저의 질문 업데이트 시 에러 페이지로 이동한다")
    public void updateArticleNotFoundTest() throws Exception {
        // when
        ResultActions actions = mockMvc.perform(put("/articles/0")
            .session(session)
            .param("title", "otherTitle")
            .param("contents", "otherContents")
            .accept(MediaType.TEXT_HTML));

        // then
        actions.andExpect(status().isOk())
            .andExpect(model().attribute("status", ErrorCode.ARTICLE_NOT_FOUND.getHttpStatus()))
            .andExpect(model().attribute("message", ErrorCode.ARTICLE_NOT_FOUND.getMessage()))
            .andExpect(view().name("error/index"));
    }

    @Test
    @DisplayName("다른 유저의 세션 정보, 질문 변경 사항과 질문 id 로 유저의 질문 업데이트 시 에러 페이지로 이동한다")
    public void updateArticleValidateTest() throws Exception {
        // given
        articleSetUp.saveArticle(article);
        session.setAttribute(SessionUser.SESSION_KEY, sessionOther);

        // when
        ResultActions actions = mockMvc.perform(put("/articles/1")
            .session(session)
            .param("title", "otherTitle")
            .param("contents", "otherContents")
            .accept(MediaType.TEXT_HTML));

        // then
        actions.andExpect(status().isOk())
            .andExpect(
                model().attribute("status", ErrorCode.INVALID_ARTICLE_WRITER.getHttpStatus()))
            .andExpect(model().attribute("message", ErrorCode.INVALID_ARTICLE_WRITER.getMessage()))
            .andExpect(view().name("error/index"));
    }

    @Test
    @DisplayName("세션 정보와 질문 id 로 유저의 질문을 삭제하고 첫 페이지로 이동한다")
    public void deleteArticleTest() throws Exception {
        // when
        Article savedArticle = articleSetUp.saveArticle(this.article);

        ResultActions actions = mockMvc.perform(delete("/articles/" + savedArticle.getArticleId())
            .session(session)
            .accept(MediaType.TEXT_HTML));

        // then
        actions.andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/"));
    }

    @Test
    @DisplayName("세션 정보와 존재하지 않는 질문 id 로 유저의 질문을 삭제하면 에러 페이지로 이동한다")
    public void deleteArticleNotFoundTest() throws Exception {
        // when
        ResultActions actions = mockMvc.perform(delete("/articles/0")
            .session(session)
            .accept(MediaType.TEXT_HTML));

        // then
        actions.andExpect(status().isOk())
            .andExpect(model().attribute("status", ErrorCode.ARTICLE_NOT_FOUND.getHttpStatus()))
            .andExpect(model().attribute("message", ErrorCode.ARTICLE_NOT_FOUND.getMessage()))
            .andExpect(view().name("error/index"));
    }

    @Test
    @DisplayName("세션 정보와 일치하지 않는 유저가 작성한 질문 id 로 유저의 질문을 삭제하면 에러 페이지로 이동한다")
    public void deleteArticleValidateTest() throws Exception {
        // given
        Article savedArticle = articleSetUp.saveArticle(this.article);
        session.setAttribute(SessionUser.SESSION_KEY, sessionOther);

        // when
        ResultActions actions = mockMvc.perform(delete("/articles/" + savedArticle.getArticleId())
            .session(session)
            .accept(MediaType.TEXT_HTML));

        // then
        actions.andExpect(status().isOk())
            .andExpect(
                model().attribute("status", ErrorCode.INVALID_ARTICLE_WRITER.getHttpStatus()))
            .andExpect(model().attribute("message", ErrorCode.INVALID_ARTICLE_WRITER.getMessage()))
            .andExpect(view().name("error/index"));
    }

    @Test
    @DisplayName("유저가 작성한 댓글만 달린 질문을 삭제하면 메인 페이지로 이동된다")
    public void deleteArticleReplyUserOnlyTest() throws Exception {
        // given
        User savedUser = articleSetUp.saveUser(user);
        Article savedArticle = articleSetUp.saveArticle(article);
        Reply savedReply = articleSetUp.saveReply(
            Reply.createWithInput(savedArticle.getArticleId(), savedUser.getUserId(),
                "comment"));

        // when
        ResultActions actions = mockMvc.perform(delete("/articles/" + savedArticle.getArticleId())
            .session(session)
            .accept(MediaType.TEXT_HTML));

        // then
        actions.andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/"));
    }

    @Test
    @DisplayName("다른 유저가 작성한 댓글이 달린 질문을 삭제하면 에러 페이지로 이동된다")
    public void deleteArticleReplyOtherTest() throws Exception {
        // given
        User savedUser = articleSetUp.saveUser(user);
        User savedOther = articleSetUp.saveUser(
            User.createWithInput("otherId", "otherPassword", "otherName",
                "other@exasmple.com"));

        Article savedArticle = articleSetUp.saveArticle(article);

        Reply userReply = articleSetUp.saveReply(
            Reply.createWithInput(savedArticle.getArticleId(), savedUser.getUserId(),
                "userComment"));
        Reply otherReply = articleSetUp.saveReply(
            Reply.createWithInput(savedArticle.getArticleId(), savedOther.getUserId(),
                "otherComment"));

        // when
        ResultActions actions = mockMvc.perform(
            delete("/articles/" + article.getArticleId())
                .session(session)
                .accept(MediaType.TEXT_HTML));

        // then
        actions.andExpect(status().isOk())
            .andExpect(
                model().attribute("status", ErrorCode.INVALID_ARTICLE_DELETE.getHttpStatus()))
            .andExpect(model().attribute("message", ErrorCode.INVALID_ARTICLE_DELETE.getMessage()))
            .andExpect(view().name("error/index"));
    }

    @Test
    @DisplayName("댓글을 작성하고 저장한다")
    public void createAnswerTest() throws Exception {
        // given
        articleSetUp.saveArticle(article);
        articleSetUp.saveUser(user);

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
        Article savedArticle = articleSetUp.saveArticle(article);
        User savedUser = articleSetUp.saveUser(user);
        Reply savedReply = articleSetUp.saveReply(
            Reply.createWithInput(savedArticle.getArticleId(), savedUser.getUserId(),
                "comment"));

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
        Article savedArticle = articleSetUp.saveArticle(this.article);
        User savedUser = articleSetUp.saveUser(this.user);
        Reply savedReply = articleSetUp.saveReply(
            Reply.createWithInput(savedArticle.getArticleId(), savedUser.getUserId(),
                "comment"));

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
    public static class ArticleSetUp {

        private final ArticleRepository articleRepository;
        private final UserRepository userRepository;
        private final ReplyRepository replyRepository;

        public ArticleSetUp(ArticleRepository articleRepository, UserRepository userRepository,
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

        public void rollback() {
            articleRepository.deleteAll();
        }
    }
}
