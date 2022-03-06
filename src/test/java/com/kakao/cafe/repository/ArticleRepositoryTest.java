package com.kakao.cafe.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.kakao.cafe.domain.article.Article;

class ArticleRepositoryTest {

    ArticleRepository articleRepository;

    @BeforeEach
    void init() {
        articleRepository = new ArticleRepository(new ArrayList<>());
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