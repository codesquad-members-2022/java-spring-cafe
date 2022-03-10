package com.kakao.cafe.repository;

import static org.assertj.core.api.Assertions.*;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.domain.User;
import java.util.List;
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

    @Test
    void findAll() {
        //given
        User loggedInUser = new User("test@user.com", "testuser", "test");
        for (int i = 1; i <= 10; i++) {
            String title = "test title" + i;
            String content = "test content" + i;
            Article article = new Article(loggedInUser, title, content);
            memoryArticleRepository.save(article);
        }

        //when
        List<Article> articles = memoryArticleRepository.findAll();

        //then
        assertThat(articles.size()).isEqualTo(10);
    }
}
