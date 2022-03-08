package com.kakao.cafe.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.kakao.cafe.domain.Article;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ArticleRepositoryTest {

    ArticleRepository articleRepository;

    Article article;

    @BeforeEach
    void setUp() {
        articleRepository = new MemoryArticleRepository();
        article = new Article.ArticleBuilder()
            .setWriter("testWriter")
            .setContents("testContent")
            .setTitle("testTitle")
            .build();
        articleRepository.save(article);
    }

    @Test
    @DisplayName("게시글 아이디를 통해 레포지토리에서 게시글 객체를 검색할 수 있다.")
    void 게시글_객체_조회_테스트() {
        // given
        String articleId = "1";

        // when
        Article resultArticle = articleRepository.findById(articleId).get();

        // then
        assertThat(resultArticle.getTitle()).isEqualTo(article.getTitle());
        assertThat(resultArticle.getWriter()).isEqualTo(article.getWriter());
        assertThat(resultArticle.getContents()).isEqualTo(article.getContents());
    }

    @Test
    @DisplayName("모든 게시글 객체를 레포지토리에서 조회할 수 있다.")
    void 모든_게시글_조회_테스트() {
        // given
        Article secondArticle = new Article.ArticleBuilder()
            .setWriter("testWriter2")
            .setContents("testContent2")
            .setTitle("testTitle2")
            .build();

        // when
        articleRepository.save(secondArticle);

        // then
        assertThat(articleRepository.findAll()).hasSize(2);
        assertThat(articleRepository.findAll()).contains(article);
        assertThat(articleRepository.findAll()).contains(secondArticle);
    }
}
