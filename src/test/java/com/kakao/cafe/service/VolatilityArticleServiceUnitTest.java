package com.kakao.cafe.service;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.repository.Repository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class VolatilityArticleServiceUnitTest {

    @InjectMocks
    VolatilityArticleService articleService;

    @Mock
    Repository<Article, Integer> repository;

    @Test
    @DisplayName("요청 받은 질문 글을 repository 에 저장한다.")
    void writeArticle() {
        // given
        Article article = new Article("writer", "title", "contents");
        given(repository.save(article))
                .willReturn(Optional.of(article));

        // when
        Article newArticle = articleService.add(article);

        // then
        assertThat(newArticle).isEqualTo(article);

        verify(repository).save(article);
    }
}
