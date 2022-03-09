package com.kakao.cafe.service;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.exception.NotFoundException;
import com.kakao.cafe.repository.ArticleRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @Mock // 대역을 생성한 후
    private ArticleRepository articleRepository;

    @InjectMocks // 생성시 대역 주입
    private ArticleService articleService;

    Article article1;

    @BeforeEach
    void init() {
        article1 = new Article("Shine", "title1", "content1", LocalDateTime.now());
        article1.setId(1L);
    }

    @Test
    public void saveTest() {
        // given
        given(articleRepository.save(article1)).willReturn(1L);

        // when
        Long articleId = articleService.addArticle(article1);

        // then
        assertThat(articleId).isEqualTo(1L);
    }

    @Test
    public void findAllArticlesTest() {
        // given
        given(articleRepository.findAll()).willReturn(Arrays.asList(article1));

        // when
        List<Article> articles = articleService.findArticles();

        // then
        assertThat(articles).contains(article1);
    }

    @Test
    public void findArticleByIdTest() {
        // given
        given(articleRepository.findById(any())).willReturn(Optional.of(article1));

        // when
        Article findArticle = articleService.findArticleById(article1.getId());

        // then
        assertThat(findArticle).isEqualTo(article1);
    }

    @Test
    @DisplayName("존재하지 않는 질문 조회시 예외 던지기")
    public void findArticleValidateTest() {
        // given
        given(articleRepository.findById(any())).willReturn(Optional.empty());

        // when
        assertThatThrownBy(() -> articleService.findArticleById(any())).isInstanceOf(NotFoundException.class);
    }
}
