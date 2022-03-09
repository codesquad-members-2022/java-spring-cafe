package com.kakao.cafe.unit.service.mock;

import static org.assertj.core.api.BDDAssertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.kakao.cafe.domain.Article;
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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("ArticleService mock 단위 테스트")
public class ArticleServiceTest {

    @InjectMocks
    private ArticleService articleService;

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private UserRepository userRepository;

    Article article;

    @BeforeEach
    public void setUp() {
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

        given(userRepository.findByUserId(any(String.class)))
            .willReturn(Optional.of(new Builder()
                .userId("writer")
                .password("userPassword")
                .name("userName")
                .email("email@example.com")
                .build()));

        given(articleRepository.save(any(Article.class)))
            .willReturn(article);

        // when
        Article savedArticle = articleService.write(articleForm);

        // then
        then(savedArticle).isEqualTo(article);
    }

    @Test
    @DisplayName("질문을 작성할 때 유저아이디가 존재하지 않으면 예외 처리한다")
    public void writeValidationTest() {
        // given
        ArticleForm articleForm = new ArticleForm("writer", "title", "contents");

        given(userRepository.findByUserId(any(String.class)))
            .willThrow(new NotFoundException(ErrorCode.USER_NOT_FOUND));

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
        // given
        given(articleRepository.findAll())
            .willReturn(List.of(article));

        // when
        List<Article> articles = articleService.findArticles();

        // then
        then(articles).containsExactlyElementsOf(List.of(article));
    }

    @Test
    @DisplayName("질문 id 로 저장소에 저장된 질문을 조회한다")
    public void findArticleTest() {
        // given
        given(articleRepository.findById(any()))
            .willReturn(Optional.of(article));

        // when
        Article findArticle = articleService.findArticle(article.getArticleId());

        // then
        then(findArticle).isEqualTo(article);
    }

    @Test
    @DisplayName("존재하지 않는 질문 id 로 조회하면 경우 예외를 반환한다")
    public void findArticleValidateTest() {
        // given
        given(articleRepository.findById(any()))
            .willReturn(Optional.empty());

        // when
        Throwable throwable = catchThrowable(() -> articleService.findArticle(any()));

        // then
        then(throwable)
            .isInstanceOf(NotFoundException.class)
            .hasMessage(ErrorCode.ARTICLE_NOT_FOUND.getMessage());
    }

}
