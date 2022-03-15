package com.kakao.cafe.unit.service.mock;

import static org.assertj.core.api.BDDAssertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.domain.Reply;
import com.kakao.cafe.dto.ArticleResponse;
import com.kakao.cafe.dto.ArticleSaveRequest;
import com.kakao.cafe.exception.ErrorCode;
import com.kakao.cafe.exception.InvalidRequestException;
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
    private ReplyRepository replyRepository;

    private Article article;
    private Reply reply;
    private ArticleResponse articleResponse;
    private SessionUser sessionUser;
    private SessionUser sessionOther;
    private ArticleSaveRequest request;

    @BeforeEach
    public void setUp() {
        article = new Article(1, "writer", "title", "contents", LocalDateTime.now());
        reply = new Reply(1, 1, "writer", "comment", LocalDateTime.now());

        articleResponse = new ArticleResponse(1, "writer", "title", "contents",
            LocalDateTime.now());
        sessionUser = new SessionUser(1, "writer", "userPassword", "userName",
            "user@example.com");
        sessionOther = new SessionUser(1, "otherId", "otherPassword", "otherName",
            "other@example.com");
        request = new ArticleSaveRequest("writer", "otherTitle", "otherContents");
    }

    @Test
    @DisplayName("질문을 작성한 후 저장소에 저장한다")
    public void writeTest() {
        // given
        ArticleSaveRequest request = new ArticleSaveRequest("writer", "title", "contents");

        given(articleRepository.save(any(Article.class)))
            .willReturn(article);

        // when
        ArticleResponse savedArticle = articleService.write(sessionUser, request);

        // then
        then(savedArticle).isEqualTo(articleResponse);
    }

    /*
    유저 아이디가 존재하지 않는 경우는 불가능하므로 제거 필요

    @Test
    @DisplayName("질문을 작성할 때 유저아이디가 존재하지 않으면 예외 처리한다")
    public void writeValidationTest() {
        // given
        ArticleSaveRequest request = new ArticleSaveRequest("writer", "title", "contents");

        given(userRepository.findByUserId(any(String.class)))
            .willThrow(new NotFoundException(ErrorCode.USER_NOT_FOUND));

        // when
        Throwable throwable = catchThrowable(() -> articleService.write(sessionUser, request));

        // when
        then(throwable)
            .isInstanceOf(NotFoundException.class)
            .hasMessage(ErrorCode.USER_NOT_FOUND.getMessage());
    }

     */

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

        given(replyRepository.findByArticleId(any()))
            .willReturn(List.of(reply));

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

    @Test
    @DisplayName("유저 정보와 질문 id 로 유저의 질문을 조회한다")
    public void mapUserArticleTest() {
        // given
        given(articleRepository.findById(any()))
            .willReturn(Optional.of(article));

        // when
        ArticleResponse findArticle = articleService.mapUserArticle(sessionUser,
            article.getArticleId());

        // then
        then(findArticle.getArticleId()).isEqualTo(1);
        then(findArticle.getWriter()).isEqualTo("writer");
        then(findArticle.getTitle()).isEqualTo("title");
        then(findArticle.getContents()).isEqualTo("contents");
    }

    @Test
    @DisplayName("유저 정보와 존재하지 않는 질문 id 로 유저의 질문을 조회하면 예외를 반환한다")
    public void mapUserArticleNotFoundTest() {
        // given
        given(articleRepository.findById(any()))
            .willThrow(new NotFoundException(ErrorCode.ARTICLE_NOT_FOUND));

        // when
        Throwable throwable = catchThrowable(
            () -> articleService.mapUserArticle(sessionUser, article.getArticleId()));

        // then
        then(throwable)
            .isInstanceOf(NotFoundException.class)
            .hasMessage(ErrorCode.ARTICLE_NOT_FOUND.getMessage());
    }


    @Test
    @DisplayName("다른 유저 정보와 질문 id 로 유저의 질문을 조회하면 예외를 반환한다")
    public void mapUserArticleValidateTest() {
        // given
        given(articleRepository.findById(any(Integer.class)))
            .willReturn(Optional.of(article));

        // when
        Throwable throwable = catchThrowable(
            () -> articleService.mapUserArticle(sessionOther, article.getArticleId()));

        // then
        then(throwable)
            .isInstanceOf(InvalidRequestException.class)
            .hasMessage(ErrorCode.INVALID_ARTICLE_WRITER.getMessage());
    }

    @Test
    @DisplayName("유저 정보, 질문 변경 사항과 질문 id 로 유저의 질문을 업데이트한다")
    public void updateArticleTest() {
        Article result = new Article(1, "writer", "otherTitle", "otherContents",
            LocalDateTime.now());

        // given
        given(articleRepository.findById(any()))
            .willReturn(Optional.of(article));

        given(articleRepository.save(any()))
            .willReturn(result);

        // when
        ArticleResponse updatedArticle = articleService.updateArticle(sessionUser, request,
            article.getArticleId());

        // then
        then(updatedArticle.getArticleId()).isEqualTo(1);
        then(updatedArticle.getWriter()).isEqualTo("writer");
        then(updatedArticle.getTitle()).isEqualTo("otherTitle");
        then(updatedArticle.getContents()).isEqualTo("otherContents");
    }

    @Test
    @DisplayName("유저 정보, 질문 변경 사항과 존재하지 않는 질문 id 로 유저의 질문을 업데이트 시 예외를 반환한다")
    public void updateArticleNotFoundTest() {
        // given
        given(articleRepository.findById(any()))
            .willThrow(new NotFoundException(ErrorCode.ARTICLE_NOT_FOUND));

        // when
        Throwable throwable = catchThrowable(
            () -> articleService.updateArticle(sessionUser, request, article.getArticleId()));

        // then
        then(throwable)
            .isInstanceOf(NotFoundException.class)
            .hasMessage(ErrorCode.ARTICLE_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("다른 유저 정보, 질문 변경 사항과 질문 id 로 유저의 질문을 업데이트 시 예외를 반환한다")
    public void updateArticleValidateTest() {
        // given
        given(articleRepository.findById(any()))
            .willReturn(Optional.of(article));

        // when
        Throwable throwable = catchThrowable(
            () -> articleService.updateArticle(sessionOther, request, article.getArticleId()));

        // then
        then(throwable)
            .isInstanceOf(InvalidRequestException.class)
            .hasMessage(ErrorCode.INVALID_ARTICLE_WRITER.getMessage());
    }

    @Test
    @DisplayName("유저 정보와 질문 id 로 유저의 질문을 삭제한다")
    public void deleteArticleTest() {
        // given
        given(articleRepository.findById(any()))
            .willReturn(Optional.of(article));

        // when
        articleService.deleteArticle(sessionUser, article.getArticleId());
    }

    @Test
    @DisplayName("유저 정보와 존재하지 않는 질문 id 로 유저의 질문을 삭제 시 예외를 반환한다")
    public void deleteArticleNotFoundTest() {
        // given
        given(articleRepository.findById(any()))
            .willThrow(new NotFoundException(ErrorCode.ARTICLE_NOT_FOUND));

        // when
        Throwable throwable = catchThrowable(
            () -> articleService.deleteArticle(sessionUser, article.getArticleId()));

        // then
        then(throwable)
            .isInstanceOf(NotFoundException.class)
            .hasMessage(ErrorCode.ARTICLE_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("다른 유저 정보와 질문 id 로 유저의 질문을 삭제 시 예외를 반환한다")
    public void deleteArticleValidateTest() {
        // given
        given(articleRepository.findById(any()))
            .willReturn(Optional.of(article));

        // when
        Throwable throwable = catchThrowable(
            () -> articleService.deleteArticle(sessionOther, article.getArticleId()));

        // then
        then(throwable)
            .isInstanceOf(InvalidRequestException.class)
            .hasMessage(ErrorCode.INVALID_ARTICLE_WRITER.getMessage());
    }
}
