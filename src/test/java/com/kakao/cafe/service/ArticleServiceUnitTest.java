package com.kakao.cafe.service;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.dto.NewArticleParam;
import com.kakao.cafe.repository.DomainRepository;
import com.kakao.cafe.util.Mapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ArticleServiceUnitTest {

    @InjectMocks
    ArticleService articleService;

    @Mock
    DomainRepository<Article, Integer> repository;

    Mapper<Article> articleMapper = new Mapper<>();

    @Test
    @DisplayName("질문 글 목록을 반환한다.")
    void searchAll() {
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
    void add() {
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
    @DisplayName("인자로 받은 id에 해당하는 글을 저장소에서 읽어와 반환한다.")
    void search() {
        // given
        Integer id = 1;
        Article article = new Article(id, "writer", "title", "contents", LocalDate.now());
        given(repository.findOne(id)).willReturn(Optional.ofNullable(article));

        // when
        Article result = articleService.search(id);

        // then
        assertThat(result).usingRecursiveComparison().isEqualTo(article);
        verify(repository).findOne(id);
    }
}
