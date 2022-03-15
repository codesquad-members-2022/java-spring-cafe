package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Article;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemoryArticleRepositoryTest {

    MemoryArticleRepository repository = new MemoryArticleRepository();

    @AfterEach
    public void afterEach() {
        repository.clearArticles();
    }

    @Test
    void save() {
        Article article = new Article("testTitle", "testContents", "testAuthor");
        Article savedArticle = repository.save(article);
        assertThat(savedArticle).isEqualTo(article);
    }

    @Test
    void findByid() {
        Article article = new Article("testTitle", "testContents", "testAuthor");
        Article savedArticle = repository.save(article);
        Article foundArticle = repository.findByid(savedArticle.getId()).orElse(null);
        assertThat(foundArticle).isNotNull();
        assertThat(foundArticle).isEqualTo(savedArticle);
    }

    @Test
    void findAll() {
        List<Article> articles = new ArrayList<>();
        Article articleOne = new Article("title1", "content1", "author1");
        Article articleTwo = new Article("title2", "content2", "author2");
        articles.add(articleOne);
        articles.add(articleTwo);

        repository.save(articleOne);
        repository.save(articleTwo);

        List<Article> returnedArticles = repository.findAll();

        for (int i = 0; i < articles.size(); i++) {
            assertThat(returnedArticles.get(i)).isEqualTo(articles.get(i));
        }
    }
}