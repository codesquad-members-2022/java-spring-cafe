package com.kakao.cafe.unit.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.repository.ArticleCollectionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ArticleCollectionRepositoryTest {

    @InjectMocks
    private ArticleCollectionRepository articleRepository;

    Article article;

    @BeforeEach
    public void setUp() {
        article = new Article("writer", "title", "content");
    }

    @Test
    @DisplayName("질문 객체를 저장소에 저장한다")
    public void articleSaveTest() {
        // when
        Article savedArticle = articleRepository.save(article);

        // then
        assertThat(savedArticle).isEqualTo(article);
    }

}
