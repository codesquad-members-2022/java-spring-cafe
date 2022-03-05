package com.kakao.cafe.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.repository.ArticleRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ArticleServiceTest {
    @InjectMocks
    private ArticleService articleService;

    @Mock
    private ArticleRepository articleRepository;

    private Article article;

    @BeforeEach
    void setup() {
        article = new Article("ikjo", "java", "java is fun");
    }

    @DisplayName("주어진 article 객체의 게시글 정보 데이터를 저장한다.")
    @Test
    void 게시글_저장() {
        // given
        given(articleRepository.save(article)).willReturn(article);

        // when
        Article result = articleService.upload(article);

        // then
        assertThat(result.getWriter()).isEqualTo("ikjo");
        assertThat(result.getTitle()).isEqualTo("java");
        assertThat(result.getContents()).isEqualTo("java is fun");
    }

    @DisplayName("게시글 ID로 해당 게시글 정보 데이터를 조회한다.")
    @Test
    void 특정_게시글_정보_조회() {
        // given
        article.setId(1);
        given(articleRepository.findById(1)).willReturn(article);

        // when
        Article result = articleService.findOne(1);

        // then
        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getWriter()).isEqualTo("ikjo");
        assertThat(result.getTitle()).isEqualTo("java");
        assertThat(result.getContents()).isEqualTo("java is fun");
    }

    @DisplayName("게시글 정보 2개를 모두 조회한다.")
    @Test
    void 모든_게시글_정보_조회() {
        // given
        Article otherArticle = new Article("ikjo", "python", "python is fun");
        given(articleRepository.findAll()).willReturn(List.of(article, otherArticle));

        // when
        List<Article> result = articleService.findAll();

        // then
        assertThat(result.size()).isEqualTo(2);
    }
}
