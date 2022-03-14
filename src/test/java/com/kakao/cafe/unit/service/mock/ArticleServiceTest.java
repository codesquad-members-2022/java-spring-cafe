package com.kakao.cafe.unit.service.mock;

import static org.assertj.core.api.BDDAssertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.domain.User;
import com.kakao.cafe.dto.ArticleResponse;
import com.kakao.cafe.dto.ArticleSaveRequest;
import com.kakao.cafe.exception.ErrorCode;
import com.kakao.cafe.exception.NotFoundException;
import com.kakao.cafe.repository.ArticleRepository;
import com.kakao.cafe.repository.UserRepository;
import com.kakao.cafe.service.ArticleService;
import java.time.LocalDateTime;
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
    ArticleResponse articleResponse;

    @BeforeEach
    public void setUp() {
        article = new Article(1, "writer", "title", "contents", LocalDateTime.now());
        articleResponse = new ArticleResponse(1, "writer", "title", "contents",
            LocalDateTime.now());
    }

    @Test
    @DisplayName("질문을 작성한 후 저장소에 저장한다")
    public void writeTest() {
        // given
        ArticleSaveRequest request = new ArticleSaveRequest("writer", "title", "contents");

        given(userRepository.findByUserId(any(String.class)))
            .willReturn(
                Optional.of(new User("userId", "userPassword", "userName", "user@example.com")));

        given(articleRepository.save(any(Article.class)))
            .willReturn(article);

        // when
        ArticleResponse savedArticle = articleService.write(request);

        // then
        then(savedArticle).isEqualTo(articleResponse);
    }

    @Test
    @DisplayName("질문을 작성할 때 유저아이디가 존재하지 않으면 예외 처리한다")
    public void writeValidationTest() {
        // given
        ArticleSaveRequest request = new ArticleSaveRequest("writer", "title", "contents");

        given(userRepository.findByUserId(any(String.class)))
            .willThrow(new NotFoundException(ErrorCode.USER_NOT_FOUND));

        // when
        Throwable throwable = catchThrowable(() -> articleService.write(request));

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
        List<ArticleResponse> articles = articleService.findArticles();

        // then
        then(articles).containsExactlyElementsOf(List.of(articleResponse));
    }

    @Test
    @DisplayName("질문 id 로 저장소에 저장된 질문을 조회한다")
    public void findArticleTest() {
        // given
        given(articleRepository.findById(any()))
            .willReturn(Optional.of(article));

        // when
        ArticleResponse findArticle = articleService.findArticle(1);

        // then
        then(findArticle).isEqualTo(articleResponse);
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
