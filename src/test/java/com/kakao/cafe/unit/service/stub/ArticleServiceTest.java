package com.kakao.cafe.unit.service.stub;

import static org.assertj.core.api.BDDAssertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.domain.User;
import com.kakao.cafe.domain.User.Builder;
import com.kakao.cafe.dto.ArticleForm;
import com.kakao.cafe.exception.ErrorCode;
import com.kakao.cafe.exception.NotFoundException;
import com.kakao.cafe.repository.ArticleRepository;
import com.kakao.cafe.repository.UserRepository;
import com.kakao.cafe.service.ArticleService;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("ArticleService stub 단위 테스트")
public class ArticleServiceTest {

    private static class ArticleStubRepository implements ArticleRepository {

        Article article = new Article.Builder()
            .articleId(1)
            .writer("writer")
            .title("title")
            .contents("contents")
            .build();

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
    }

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
            return Optional.of(new Builder()
                .userId("writer")
                .password("userPassword")
                .name("userName")
                .email("email@example.com")
                .build());
        }

        @Override
        public void deleteAll() {

        }
    }

    private ArticleService articleService;
    private Article article;

    @BeforeEach
    public void setUp() {
        articleService = new ArticleService(new ArticleStubRepository(), new UserStubRepository());

        article = new Article.Builder()
            .articleId(1)
            .writer("writer")
            .title("title")
            .contents("contents")
            .build();
    }

    @Test
    @DisplayName("질문을 작성한 후 저장소에 저장한다")
    public void writeTest() {
        // given
        ArticleForm articleForm = new ArticleForm("writer", "title", "contents");

        // when
        Article savedArticle = articleService.write(articleForm);

        // then
        then(savedArticle).isEqualTo(article);
    }

    @Test
    @DisplayName("질문을 작성할 때 유저아이디가 존재하지 않으면 예외 처리한다")
    public void writeValidationTest() {
        // given
        ArticleForm articleForm = new ArticleForm("none", "title", "contents");

        // when
        Throwable throwable = catchThrowable(() -> articleService.write(articleForm));

        // when
        then(throwable)
            .isInstanceOf(NotFoundException.class)
            .hasMessage(ErrorCode.USER_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("저장소에 저장된 모든 질문을 조회한다")
    public void findArticlesTest() {
        // when
        List<Article> articles = articleService.findArticles();

        // then
        then(articles).containsExactlyElementsOf(List.of(article));
    }

    @Test
    @DisplayName("질문 id 로 저장소에 저장된 질문을 조회한다")
    public void findArticleTest() {
        // when
        Article findArticle = articleService.findArticle(article.getArticleId());

        // then
        then(findArticle).isEqualTo(article);
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

}
