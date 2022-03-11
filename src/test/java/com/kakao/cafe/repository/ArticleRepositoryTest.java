package com.kakao.cafe.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.kakao.cafe.domain.article.Article;

class ArticleRepositoryTest {

    MemoryArticleRepository articleRepository;

    @BeforeEach
    void init() {
        articleRepository = new MemoryArticleRepository();
    }

    @DisplayName("정상 저장되는지 확인한다.")
    @Test
    void save() {
        // given
        Article article1 = new Article("lee", "title1", "contents");

        // when
        articleRepository.save(article1);

        // then
        List<Article> all = articleRepository.findAll();
        Article article = all.get(0);
        assertThat(article1).isEqualTo(article);
    }
}
