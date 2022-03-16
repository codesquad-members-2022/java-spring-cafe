package com.kakao.cafe.repository;

import static org.assertj.core.api.Assertions.*;

import com.kakao.cafe.domain.Article;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemoryArticleRepositoryTest {

    MemoryArticleRepository memoryArticleRepository = new MemoryArticleRepository();

    @BeforeEach
    void setUp() {
        memoryArticleRepository.clear();
    }

    @Test
    @DisplayName("게시글을 저장하면, 게시글을 ArrayList에 저장하고 id를 리턴한다.")
    void save() {
        //given
        Article validArticle = new Article("Anonymous", "test title", "test content");

        //when
        int savedArticleIndex = memoryArticleRepository.save(validArticle);

        //then
        assertThat(savedArticleIndex).isEqualTo(1);
    }

    @Test
    @DisplayName("게시글을 존재하는 id로 조회하면, 해당 id를 가진 게시글을 리턴한다.")
    void findById_validId() {
        //given
        Article givenArticle = new Article("Anonymous", "test title", "test content");
        int givenId = memoryArticleRepository.save(givenArticle);

        //when
        Optional<Article> result = memoryArticleRepository.findById(givenId);

        //then
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().hasSameId(givenId)).isTrue();
    }

    @Test
    @DisplayName("게시글을 존재하지 않는 id로 조회하면, 결과를 반환하지 않는다.")
    void findById_invalidId() {
        //given
        Article givenArticle = new Article("Anonymous", "test title", "test content");
        memoryArticleRepository.save(givenArticle);

        //when
        Optional<Article> result = memoryArticleRepository.findById(-1);

        //then
        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("게시글을 전부 조회하면, 저장된 게시글 전부를 List에 담아 리턴한다.")
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
