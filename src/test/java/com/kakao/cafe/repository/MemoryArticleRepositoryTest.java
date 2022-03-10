package com.kakao.cafe.repository;

import static org.assertj.core.api.Assertions.*;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MemoryArticleRepositoryTest {

    MemoryArticleRepository memoryArticleRepository = new MemoryArticleRepository();

    @BeforeEach
    void setUp() {
        memoryArticleRepository.clear();
    }

    @Test
    void save() {
        //given
        User loggedInUser = new User("test@user.com", "testuser", "test");
        Article validArticle = new Article(loggedInUser, "test title", "test content");

        //when
        int savedArticleIndex = memoryArticleRepository.save(validArticle);

        //then
        assertThat(savedArticleIndex).isEqualTo(0);
    }
}
