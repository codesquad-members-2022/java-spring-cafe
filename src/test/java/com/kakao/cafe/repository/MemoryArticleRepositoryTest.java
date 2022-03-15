package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Article;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class MemoryArticleRepositoryTest {
    ArticleRepository articleRepository;

    @BeforeEach
    public void beforeEach() {
        articleRepository = new MemoryArticleRepository();
        Article article1 = new Article("첫번째 게시글 제목", "호눅스", "자바 스프링 재밌다.");
        Article article2 = new Article("두번째 게시글 제목", "크롱", "자바스크립트 리액트 재밌다.");

        articleRepository.save(article1);
        articleRepository.save(article2);
    }

    @AfterEach
    public void afterEach() {
        articleRepository.clearStore();
    }

    @Test
    @DisplayName("Article 객체가 MemoryArticleRepository에 잘 저장되는가")
    void save() {
        Article savedArticle1 = articleRepository.findByIndex(0).orElseThrow();
        Article savedArticle2 = articleRepository.findByIndex(1).orElseThrow();

        assertThat(articleRepository.size()).isEqualTo(2);
        assertThat("첫번째 게시글 제목").isEqualTo(savedArticle1.getTitle());
        assertThat("두번째 게시글 제목").isEqualTo(savedArticle2.getTitle());
    }

    @Test
    @DisplayName("인덱스가 초과되면, IndexOutOfBoundsException이 뜨는가")
    void findByIndex() {
        assertThatThrownBy(() -> articleRepository.findByIndex(3))
                .isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    @DisplayName("모든 Article이 잘 불러와지는가")
    void findAll() {
        List<Article> articles = articleRepository.findAll();

        assertThat(articles.size()).isEqualTo(2);
        assertThat("첫번째 게시글 제목").isEqualTo(articles.get(0).getTitle());
        assertThat("두번째 게시글 제목").isEqualTo(articles.get(1).getTitle());

    }
}
