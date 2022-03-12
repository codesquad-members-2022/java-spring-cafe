package com.kakao.cafe.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.kakao.cafe.domain.Article;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("MemoryArticleRepository 단위 테스트")
public class MemoryArticleRepositoryTest {

    private ArticleRepository articleRepository = new MemoryArticleRepository();

    private Article article;

    @BeforeEach
    void setup() {
        article = new Article(1, "ikjo", "java", "java is fun", LocalDateTime.now());
    }

    @AfterEach
    void close() {
        articleRepository.clear();
    }

    @DisplayName("주어진 article 객체의 사용자 정보 데이터를 저장한다.")
    @Test
    void 사용자_정보_저장() {
        // when
        articleRepository.save(article);

        // then
        Article result = articleRepository.findById(1).orElseThrow(() -> new NoSuchElementException("해당하는 글이 없습니다."));
        assertThat(result).isEqualTo(article);
    }

    @DisplayName("게시글 ID로 해당 게시글 정보 데이터를 조회한다.")
    @Test
    void 특정_사용자_정보_조회() {
        // given
        articleRepository.save(article);

        // when
        Article result = articleRepository.findById(1).orElseThrow(() -> new NoSuchElementException("해당하는 글이 없습니다."));

        // then
        assertThat(result).isEqualTo(article);
    }

    @DisplayName("저장된 게시글 정보 2개를 모두 조회한다.")
    @Test
    void 모든_사용자_정보_조회() {
        // given
        articleRepository.save(article);
        articleRepository.save(new Article(2, "ikjo", "python", "python is fun", LocalDateTime.now()));

        // when
        List<Article> result = articleRepository.findAll();

        // then
        assertThat(result.size()).isEqualTo(2);
    }
}
