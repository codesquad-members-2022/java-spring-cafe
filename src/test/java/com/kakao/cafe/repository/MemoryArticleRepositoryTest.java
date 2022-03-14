package com.kakao.cafe.repository;

import static org.assertj.core.api.Assertions.*;

import com.kakao.cafe.domain.Article;
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
        Article validArticle = new Article("Anonymous", "test title", "test content");

        //when
        int savedArticleIndex = memoryArticleRepository.save(validArticle);

        //then
        assertThat(savedArticleIndex).isEqualTo(1);
    }

    @Test
    void findById() {
        //given
        Article givenArticle = new Article("Anonymous", "test title", "test content");
        int givenId = memoryArticleRepository.save(givenArticle);

        //when
        Article result = memoryArticleRepository.findById(givenId);

        //then
        assertThat(result.hasSameId(givenId)).isTrue();
    }

    @Test
    void findAll() {
        //given
        for (int i = 1; i <= 10; i++) {
            String title = "test title" + i;
            String content = "test content" + i;
            Article article = new Article("Anonymous", title, content);
            memoryArticleRepository.save(article);
        }

        //when
        List<Article> articles = memoryArticleRepository.findAll();

        //then
        assertThat(articles.size()).isEqualTo(10);
    }
}
