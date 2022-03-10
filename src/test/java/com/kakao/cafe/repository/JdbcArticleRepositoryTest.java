package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;

class JdbcArticleRepositoryTest {

    @Autowired
    ArticleRepository articleRepository;

    @Test
    void save() {
        //given
        Article article = new Article("asdf", "title", "content");
        //when
        articleRepository.save(article);
        //then
    }

    @Test
    void getArticleList() {
    }

    @Test
    void findById() {
    }

    @Test
    void clearStorage() {
    }
}