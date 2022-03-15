package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Article;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ArticleRepositoryTest {

    ArticleRepository articleRepository;

    @BeforeEach
    void beforeEach() {
        articleRepository = new MemoryArticleRepository();
    }

    @Test
    void getArticleList() {
        //given
        Article article = new Article("asdf", "title", "content");
        List<Article> articles = new ArrayList<>();
        articles.add(article);
        //when
        articleRepository.save(article);
        //then
        assertThat(articleRepository.getArticles()).isEqualTo(articles);
    }

    @Test
    void save() {
        //given
        Article article = new Article("asdf", "title", "content");
        //when
        articleRepository.save(article);
        //then
        assertThat(article).isEqualTo(articleRepository.getArticles().get(0));
    }

    @Test
    void findById() {
        //given
        Article article = new Article("asdf", "title", "content");
        articleRepository.save(article);
        //when
        Article foundArticle = articleRepository.findById(0);
        //then
        assertThat(foundArticle).isEqualTo(article);
    }

}
