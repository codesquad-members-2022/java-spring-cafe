package com.kakao.cafe.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.repository.MemoryArticleRepository;

class ArticleServiceTest {
    MemoryArticleRepository articleRepository;
    ArticleService articleService;

    @BeforeEach
    void init() {
        articleRepository = new MemoryArticleRepository();
        articleService = new ArticleService(articleRepository);
    }

    @Test
    @DisplayName("게시글을 등록하면 MemoryArticleRespository 에 저장된다")
    void add() {
        Article article = new Article("제목1", "내용1");
        int id = articleService.add(article);

        Article foundArticle = articleService.findById(1);
        assertThat(article.getTitle()).isEqualTo(foundArticle.getTitle());
        assertThat(article.getContent()).isEqualTo(foundArticle.getContent());
    }

    @Test
    @DisplayName("게시글을 등록하면 오름차순으로 id가 할당된다")
    void add_multiple_articles() {
        Article article1 = new Article("제목1", "내용1");
        int id1 = articleService.add(article1);

        Article article2 = new Article("제목2", "내용2");
        int id2 = articleService.add(article2);

        assertThat(id1).isEqualTo(1);
        assertThat(id2).isEqualTo(2);
    }
}
