package com.ttasjwi.cafe.domain.article;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryArticleRepositoryTest {

    private MemoryArticleRepository memoryArticleRepository;

    @BeforeEach
    void setUp() {
        this.memoryArticleRepository = new MemoryArticleRepository();
    }

    @Test
    @DisplayName("저장 게시글의 인덱스로 조회 -> 같은 저장게시글 반환")
    void saveTest() {
        // given
        Article article = Article.builder()
                .title("자바 두명 타요")
                .content("활활 잘 탄다~")
                .build();

        memoryArticleRepository.save(article);
        Long articleId = article.getArticleId();

        // when
        Article findArticle = memoryArticleRepository.findByArticleId(articleId).get();

        // then
        assertThat(article).isEqualTo(findArticle);
    }

    @Test
    @DisplayName("findAll -> 저장한 인원만큼의 리스트 반환")
    void findAllTest() {
        // given
        Article article1 = Article.builder()
                .title("선생님들 살려주세요")
                .content("회사 DB에 DROP DATABASE 해버렸어요;;")
                .build();

        Article article2 = Article.builder()
                .title("우리 개발자 연봉 이대로 가면")
                .content("라이더")
                .build();

        memoryArticleRepository.save(article1);
        memoryArticleRepository.save(article2);

        // when
        List<Article> list = memoryArticleRepository.findAll();
        int size = list.size();

        // then
        assertThat(size).isEqualTo(2);
    }
}