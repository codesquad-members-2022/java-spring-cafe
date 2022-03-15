package com.kakao.cafe.unit.service.stub;

import static org.assertj.core.api.BDDAssertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.domain.Reply;
import com.kakao.cafe.dto.ArticleResponse;
import com.kakao.cafe.dto.ArticleSaveRequest;
import com.kakao.cafe.exception.ErrorCode;
import com.kakao.cafe.exception.NotFoundException;
import com.kakao.cafe.repository.ArticleRepository;
import com.kakao.cafe.repository.ReplyRepository;
import com.kakao.cafe.service.ArticleService;
import com.kakao.cafe.session.SessionUser;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("ArticleService stub 단위 테스트")
public class ArticleServiceTest {

    private static class ArticleStubRepository implements ArticleRepository {

        Article article = new Article(1, "writer", "title", "contents", LocalDateTime.now());

        @Override
        public Article save(Article article) {
            return this.article;
        }

        @Override
        public List<Article> findAll() {
            return List.of(article);
        }

        @Override
        public Optional<Article> findById(Integer articleId) {
            if (articleId == null) {
                throw new NotFoundException(ErrorCode.ARTICLE_NOT_FOUND);
            }
            return Optional.of(article);
        }

        @Override
        public void deleteAll() {

        }

        @Override
        public void deleteById(Integer articleId) {

        }
    }

    /*

    private static class UserStubRepository implements UserRepository {

        @Override
        public User save(User user) {
            return null;
        }

        @Override
        public List<User> findAll() {
            return null;
        }

        @Override
        public Optional<User> findByUserId(String userId) {
            if (userId.equals("none")) {
                throw new NotFoundException(ErrorCode.USER_NOT_FOUND);
            }
            return Optional.of(new User("userId", "userPassword", "userName", "user@example.com"));
        }

        @Override
        public void deleteAll() {

        }
    }

     */

    private static class ReplyStubRepository implements ReplyRepository {

        Reply reply = new Reply(1, 1, "writer", "comment", LocalDateTime.now());

        @Override
        public Reply save(Reply reply) {
            return null;
        }

        @Override
        public Optional<Reply> findById(Integer replyId) {
            return Optional.empty();
        }

        @Override
        public List<Reply> findByArticleId(Integer articleId) {
            return List.of(reply);
        }

        @Override
        public void deleteById(Integer replyId) {

        }
    }

    private ArticleService articleService;
    private ArticleResponse articleResponse;

    @BeforeEach
    public void setUp() {
        articleService = new ArticleService(new ArticleStubRepository(), new ReplyStubRepository());

        articleResponse = new ArticleResponse(1, "writer", "title", "contents",
            LocalDateTime.now());
    }

    @Test
    @DisplayName("질문을 작성한 후 저장소에 저장한다")
    public void writeTest() {
        SessionUser sessionUser = new SessionUser(1, "userId", "userPassword", "usrName",
            "user@example.com");
        // given
        ArticleSaveRequest request = new ArticleSaveRequest("writer", "title", "contents");

        // when
        ArticleResponse savedArticle = articleService.write(sessionUser, request);

        // then
        then(savedArticle).isEqualTo(articleResponse);
    }

    /*
    유저아이디가 일치하지 않는 경우는 불가능하므로 제거 필요

    @Test
    @DisplayName("질문을 작성할 때 유저아이디가 존재하지 않으면 예외 처리한다")
    public void writeValidationTest() {
        // given
        SessionUser sessionNone = new SessionUser(1, "none", "userPassword", "userName",
            "user@example.com");

        ArticleSaveRequest request = new ArticleSaveRequest("none", "title", "contents");

        // when
        Throwable throwable = catchThrowable(() -> articleService.write(sessionNone, request));

        // when
        then(throwable)
            .isInstanceOf(NotFoundException.class)
            .hasMessage(ErrorCode.USER_NOT_FOUND.getMessage());
    }
     */

    @Test
    @DisplayName("저장소에 저장된 모든 질문을 조회한다")
    public void findArticlesTest() {
        // when
        List<ArticleResponse> articles = articleService.findArticles();

        // then
        then(articles).containsExactlyElementsOf(List.of(articleResponse));
    }

    @Test
    @DisplayName("질문 id 로 저장소에 저장된 질문을 조회한다")
    public void findArticleTest() {
        // when
        ArticleResponse findArticle = articleService.findArticle(1);

        // then
        then(findArticle).isEqualTo(articleResponse);
    }

    @Test
    @DisplayName("존재하지 않는 질문 id 로 조회하면 경우 예외를 반환한다")
    public void findArticleValidateTest() {
        // when
        Throwable throwable = catchThrowable(() -> articleService.findArticle(null));

        // then
        then(throwable)
            .isInstanceOf(NotFoundException.class)
            .hasMessage(ErrorCode.ARTICLE_NOT_FOUND.getMessage());
    }

    // TODO: ArticleService 에서 mapUserArticle, updateUserArticle, deleteUserArticle stub 테스트 필요
    // (mock 테스트는 진행 완료)

}
