package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Article;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class VolatilityArticleRepositoryTest {

    VolatilityArticleRepository repository;

    @BeforeEach
    public void beforeEach() {
        repository = new VolatilityArticleRepository();
    }

    @Test
    @DisplayName("전체 게시글 목록을 반환한다.")
    void findAll() {
        //given
        Article article1 = new Article(1, "writer1", "title1", "contents1", LocalDate.now());
        repository.save(article1);

        Article article2 = new Article(2, "writer2", "title2", "contents2", LocalDate.now());
        repository.save(article2);

        //when
        List<Article> articles = repository.findAll();

        //then
        assertThat(articles).usingRecursiveComparison().isEqualTo(List.of(article1, article2));
    }

    @Test
    @DisplayName("인자로 주어진 게시글을 저장소에 저장한다.")
    void persist() {
        //given
        Article article = new Article(1, "writer", "title", "contents", LocalDate.now());

        //when
        repository.save(article);

        //then
        Article result = repository.findById(article.getId()).get();
        assertThat(result).usingRecursiveComparison().isEqualTo(article);
    }

    @Test
    @DisplayName("인자로 주어진 ID를 가진 게시글을 저장소에서 찾아 반환한다.")
    void findById() {
        //given
        Article article = new Article(1, "writer", "title", "contents", LocalDate.now());
        repository.save(article);

        //when
        Article result = repository.findById(article.getId()).get();

        //then
        assertThat(result).usingRecursiveComparison().isEqualTo(article);
    }
}
