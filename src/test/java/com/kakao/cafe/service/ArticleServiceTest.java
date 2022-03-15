package com.kakao.cafe.service;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.repository.MemoryArticleRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ArticleServiceTest {
    MemoryArticleRepository repository = new MemoryArticleRepository();
    ArticleService articleService = new ArticleService(repository);

    @AfterEach
    public void afterEach() {
        repository.clearArticles();
    }

    @Test
    void create() {
        Article article = new Article("testTitle", "testContent", "testAuthor");
        Article savedArticle = articleService.create(article);

        Assertions.assertThat(savedArticle).isEqualTo(article);
    }

    @Test
    void findById() {
        Article article = new Article("testTitle", "testContent", "testAuthor");
        Article savedArticle = articleService.create(article);
        Article foundArticle = articleService.findById(savedArticle.getId());

        Assertions.assertThat(savedArticle).isEqualTo(foundArticle);
    }

    @Test
    void getAllArticles() {
        List<Article> articles = new ArrayList<>();
        Article articleOne = new Article("testTitleOne", "testContentOne", "testAuthorOne");
        Article articleTwo = new Article("testTitleTwo", "testContentTwo", "testAuthorTwo");
        articles.add(articleOne);
        articles.add(articleTwo);

        articleService.create(articleOne);
        articleService.create(articleTwo);

        List<Article> returnedArticles = articleService.getAllArticles();

        for (int i = 0; i < articles.size(); i++) {
            Assertions.assertThat(returnedArticles.get(i)).isEqualTo(articles.get(i));
        }


    }
}