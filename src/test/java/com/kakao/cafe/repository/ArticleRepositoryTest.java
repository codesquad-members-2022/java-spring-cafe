package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Article;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
        List<Article> articleList = new ArrayList<>();
        articleList.add(article);
        //when
        articleRepository.save(article);
        //then
        assertThat(articleList).isEqualTo(articleRepository.getArticleList());
    }

    @Test
    void save() {
        //given
        Article article = new Article("asdf", "title", "content");
        //when
        articleRepository.save(article);
        //then
        assertThat(article).isEqualTo(articleRepository.getArticleList().get(0));
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
