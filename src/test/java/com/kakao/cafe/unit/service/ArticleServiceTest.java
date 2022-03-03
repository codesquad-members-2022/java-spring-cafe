package com.kakao.cafe.unit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.exception.CustomException;
import com.kakao.cafe.exception.ErrorCode;
import com.kakao.cafe.repository.ArticleRepository;
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
public class ArticleServiceTest {

    private static final String WRITER = "writer";
    private static final String TITLE = "title";
    private static final String CONTENTS = "contents";

    @InjectMocks
    private ArticleService articleService;

    @Mock
    private ArticleRepository articleRepository;

    Article article;

    @BeforeEach
    public void setUp() {
        article = new Article(WRITER, TITLE, CONTENTS);
        article.setArticleId(1);
    }

    @Test
    @DisplayName("질문을 작성한 후 저장소에 저장한다")
    public void writeTest() {
        // given
        given(articleRepository.save(article))
            .willReturn(article);

        // when
        Article savedArticle = articleService.write(article);

        // then
        assertThat(savedArticle).isEqualTo(article);
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
        assertThat(articles).containsExactlyElementsOf(List.of(article));
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
        assertThat(findArticle).isEqualTo(article);
    }

    @Test
    @DisplayName("존재하지 않는 질문 id 로 조회하면 경우 예외를 반환한다")
    public void findArticleValidateTest() {
        // given
        given(articleRepository.findById(any()))
            .willReturn(Optional.empty());

        // when
        CustomException exception = assertThrows(CustomException.class,
            () -> articleService.findArticle(any()));

        // then
        assertThat(exception.getMessage()).isEqualTo(ErrorCode.ARTICLE_NOT_FOUND.getMessage());
    }

}
