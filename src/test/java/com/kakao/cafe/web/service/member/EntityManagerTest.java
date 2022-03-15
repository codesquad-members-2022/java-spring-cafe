package com.kakao.cafe.web.service.member;

import com.kakao.cafe.config.Database;
import com.kakao.cafe.core.domain.article.Article;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class EntityManagerTest {

    @Test
    @DisplayName("필드값이 같으면 true를 반환한다.")
    void 간단한_더티체킹_테스트_성공() {
        Article articleA = new Article(1, "루시드", "그렇다", "Jun", LocalDateTime.now(), LocalDateTime.now(), 3);
        Article articleB = new Article(1, "루시드", "그렇다", "Jun", LocalDateTime.now(), LocalDateTime.now(), 3);
        EntityManager<Article> entityManager = new EntityManager<>(new Database());
        Assertions.assertTrue(entityManager.isChanged(articleA, articleB));
    }

    @Test
    @DisplayName("필드값이 다르면 false를 반환한다.")
    void 간단한_더티체킹_테스트_실패_참조값() {
        Article articleA = new Article(1, "루시드", "그렇다", "Jun", LocalDateTime.now(), LocalDateTime.now(), 3);
        Article articleB = new Article(1, "루 시드", "그렇다", "Jun", LocalDateTime.now(), LocalDateTime.now(), 3);
        EntityManager<Article> entityManager = new EntityManager<>(new Database());
        Assertions.assertFalse(entityManager.isChanged(articleA, articleB));
    }

    @Test
    @DisplayName("필드값이 다르면 false를 반환한다.")
    void 간단한_더티체킹_테스트_실패_원시타입() {
        Article articleA = new Article(1, "루시드", "그렇다", "Jun", LocalDateTime.now(), LocalDateTime.now(), 3);
        Article articleB = new Article(2, "루시드", "그렇다", "Jun", LocalDateTime.now(), LocalDateTime.now(), 3);
        EntityManager<Article> entityManager = new EntityManager<>(new Database());
        Assertions.assertFalse(entityManager.isChanged(articleA, articleB));
    }
}