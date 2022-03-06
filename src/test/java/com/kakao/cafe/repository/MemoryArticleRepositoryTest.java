package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemoryArticleRepositoryTest {

    MemoryArticleRepository repository;
    Article article1;
    Article article2;

    @BeforeEach
    void init(){
        // given
        repository = new MemoryArticleRepository();
        article1 = new Article("test1", "test1", "test1234567899123446아아아아아아아", LocalDateTime.now());
        article2 = new Article("test2", "test1", "test1234567899123446아아아아아아아", LocalDateTime.now());
    }

    @Test
    void saveTest(){
        // when
        Long test1Id = repository.save(article1);
        Long test2Id = repository.save(article2);

        // then
        assertThat(test1Id).isEqualTo(1L);
        assertThat(test2Id).isEqualTo(2L);
    }

    @Test
    void findTest(){
        // when
        Long test1Id = repository.save(article1);
        Long test2Id = repository.save(article2);
        Article findArticle = repository.findById(test1Id).get();

        // then
        assertThat(findArticle.getId()).isEqualTo(1L);
    }

    @Test
    void findAllTest() {
        // when
        repository.save(article1);
        repository.save(article2);

        // then
        List<Article> list = repository.findAll();
        assertThat(list.size()).isEqualTo(2);
    }

    @Test
    void deleteTest() {
        // when
        Long userId = repository.save(article1);
        repository.save(article2);
        boolean isDeleted = repository.delete(userId);

        // then
        assertThat(isDeleted).isTrue();
        assertThat(repository.findById(userId).isEmpty()).isTrue();
    }

    @Test
    void updateTest() {
        // when
        repository.save(article1);
        repository.save(article2);
        Article change = new Article("test1", "after", "change", LocalDateTime.now());
        repository.update(1L, change);

        // then
        Article changeArticle = repository.findById(1L).get();
        assertThat(changeArticle.getTitle()).isEqualTo("after");
    }
}
