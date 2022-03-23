package com.kakao.cafe.domain.article;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ArticleMemoryRepositoryTest {
    ArticleMemoryRepository articleMemoryRepository;
    Article article;

    @BeforeEach
    void setUp() {
        String writer = "testWriter";
        String title = "testTitle";
        String contents = "contents";
        article = new Article(writer, title, contents);
        articleMemoryRepository = new ArticleMemoryRepository();
    }

    @Test
    @DisplayName("게시글이 저장되고 잘 조회된다")
    void save() {
        // given
        articleMemoryRepository.save(article);
        Long id = article.getId();
        // when
        Optional<Article> byId = articleMemoryRepository.findById(id);
        // then
        assertThat(byId).isPresent();
        assertThat(byId).contains(article);
        assertThat(byId.get().getTitle()).isEqualTo(article.getTitle());
    }

    @Test
    @DisplayName("게시글 목록이 내림차순으로 잘 조회된다")
    void findAllDesc() {
        // given
        String newWriter = "newWriter";
        String newTitle = "newTitle";
        String newContents = "newContents";
        Article newArticle = new Article(newWriter, newTitle, newContents);
        articleMemoryRepository.save(article);
        articleMemoryRepository.save(newArticle);

        // when
        List<Article> articleList = articleMemoryRepository.findAllDesc();

        // then
        assertThat(articleList).hasSize(2);
        assertThat(articleList.get(0)).isEqualTo(newArticle);
        assertThat(articleList.get(1)).isEqualTo(article);
    }

    @Test
    @DisplayName("게시글이 삭제된다")
    void deleteById() {
        // given
        String newWriter = "newWriter";
        String newTitle = "newTitle";
        String newContents = "newContents";
        Article newArticle = new Article(newWriter, newTitle, newContents);
        articleMemoryRepository.save(article);
        articleMemoryRepository.save(newArticle);

        // when
        articleMemoryRepository.deleteById(newArticle.getId());
        List<Article> articleList = articleMemoryRepository.findAllDesc();

        // then
        assertThat(articleList).hasSize(1);
        assertThat(articleList.get(0)).isEqualTo(article);
    }
}
