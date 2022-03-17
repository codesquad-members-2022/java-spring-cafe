package com.kakao.cafe.service;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.dto.ModifiedArticleParam;
import com.kakao.cafe.dto.NewArticleParam;
import com.kakao.cafe.exception.article.RemoveArticleException;
import com.kakao.cafe.exception.article.SaveArticleException;
import com.kakao.cafe.repository.CrudRepository;
import com.kakao.cafe.util.DomainMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.kakao.cafe.message.ArticleDomainMessage.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ArticleServiceUnitTest {

    @InjectMocks
    ArticleService articleService;

    @Mock
    CrudRepository<Article, Integer> repository;

    DomainMapper<Article> articleMapper = new DomainMapper<>();

    @Test
    @DisplayName("질문 글 목록을 반환한다.")
    void searchAllSuccess() {
        // given
        List<Article> articles = List.of(
                new Article(1, "writer1", "title1", "contents1", LocalDate.now()),
                new Article(2, "writer2", "title2", "contents2", LocalDate.now()),
                new Article(3, "writer3", "title3", "contents3", LocalDate.now())
        );
        given(repository.findAll()).willReturn(articles);

        // when
        List<Article> result = articleService.searchAll();

        // then
        assertThat(result).usingRecursiveComparison().isEqualTo(articles);
        verify(repository).findAll();
    }

    @Test
    @DisplayName("요청 받은 질문 글을 repository 에 저장한다.")
    void addSuccess() {
        // given
        NewArticleParam newArticleParam = new NewArticleParam("writer", "title", "contents");
        Article article = articleMapper.convertToDomain(newArticleParam, Article.class);
        given(repository.save(article)).willReturn(Optional.of(article));

        // when
        Article newArticle = articleService.add(newArticleParam);

        // then
        assertThat(newArticle).usingRecursiveComparison().isEqualTo(article);
        verify(repository).save(article);
    }

    @Test
    @DisplayName("게시글 수정 요청이 오면 저장소에 반영한다.")
    void updateSuccess() {
        // given
        Integer id = 1;
        LocalDate currentDate = LocalDate.now();

        ModifiedArticleParam modifiedArticleParam =
                new ModifiedArticleParam(id, "updatedWriter", "updatedTitle", "updatedContents", currentDate);

        Article updatedArticle = articleMapper.convertToDomain(modifiedArticleParam, Article.class);
        given(repository.save(updatedArticle)).willReturn(Optional.of(updatedArticle));;

        // when
        Article result = articleService.update(modifiedArticleParam);

        // then
        assertThat(result).usingRecursiveComparison().isEqualTo(updatedArticle);
        verify(repository).save(updatedArticle);
    }

    @Test
    @DisplayName("게시글 수정 요청에 따른 저장소에 반영이 실패하면 SaveArticleException 예외가 발생한다.")
    void updateFail() {
        // given
        Integer id = 1;
        LocalDate currentDate = LocalDate.now();

        ModifiedArticleParam modifiedArticleParam =
                new ModifiedArticleParam(id, "updatedWriter", "updatedTitle", "updatedContents", currentDate);

        Article updatedArticle = articleMapper.convertToDomain(modifiedArticleParam, Article.class);
        given(repository.save(updatedArticle)).willReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> articleService.update(modifiedArticleParam))
                .isInstanceOf(SaveArticleException.class)
                .hasMessage(UPDATE_FAIL_MESSAGE);

        verify(repository).save(updatedArticle);
    }

    @Test
    @DisplayName("인자로 받은 id에 해당하는 글을 저장소에서 읽어와 반환한다.")
    void searchSuccess() {
        // given
        Integer id = 1;
        Article article = new Article(id, "writer", "title", "contents", LocalDate.now());
        given(repository.findById(id)).willReturn(Optional.ofNullable(article));

        // when
        Article result = articleService.search(id);

        // then
        assertThat(result).usingRecursiveComparison().isEqualTo(article);
        verify(repository).findById(id);
    }

    @Test
    @DisplayName("인자로 받은 id에 해당하는 게시글 삭제가 실패하면 RemoveArticleException 예외가 발생한다.")
    void removeFail() {
        // given
        int id = 1;
        given(repository.deleteById(id)).willReturn(0);

        // when, then
        assertThatThrownBy(() -> articleService.remove(id))
                .isInstanceOf(RemoveArticleException.class)
                .hasMessage(REMOVE_FAIL_MESSAGE);

        verify(repository).deleteById(id);
    }
}
