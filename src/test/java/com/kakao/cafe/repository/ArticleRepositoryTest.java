package com.kakao.cafe.repository;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest //이 어노테이션 기능 알아보기
class ArticleRepositoryTest {

    private final ArticleRepository articleRepository;

    @Autowired
    ArticleRepositoryTest(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @BeforeEach
    void setUp() {
        articleRepository.deleteAll();
    }
}