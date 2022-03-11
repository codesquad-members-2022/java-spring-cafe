package com.kakao.cafe.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

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

import com.kakao.cafe.domain.article.Article;
import com.kakao.cafe.repository.MemoryArticleRepository;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @InjectMocks
    ArticleService articleService;

    @Mock
    MemoryArticleRepository articleRepository;

    Article article1;
    Article article2;

    @BeforeEach
    void init() {
        article1 = new Article("lucid", "title1", "blabla");
        article2 = new Article("elon", "title2", "blabla~~");
    }

    @DisplayName("findAll을 통해 저장된 모든 article List를 가져온다")
    @Test
    void find_all() {
        // given
        given(articleRepository.findAll())
            .willReturn(List.of(article1, article2));

        // when
        List<Article> articles = articleService.showAllArticles();

        // then
        assertThat(articles.size()).isEqualTo(2);
    }

    @DisplayName("인덱스를 전달하면 해당하는 Article이 반환된다.")
    @Test
    void show_articles_by_index() {
        // given
        when(articleRepository.findById(0L))
            .thenReturn(Optional.ofNullable(article1));

        // when
        Article article = articleService.showArticle(0);

        // then
        assertThat(article1).isEqualTo(article);
    }

    @DisplayName("없는 인덱스를 전달하면 IllegalArgumentException이 발생한다.")
    @Test
    void show_articles_throw_exception() {
        // given
        lenient().when(articleRepository.findByIndex(0))
            .thenReturn(Optional.ofNullable(article1));

        // when & then
        assertThrows(IllegalArgumentException.class, () -> {
            articleService.showArticle(1);
        });
    }
}
