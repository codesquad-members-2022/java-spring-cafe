package com.kakao.cafe.domain.article;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryArticleRepositoryTest {


    private ArticleRepository articleRepository;
    private Article article;

    @BeforeEach
    void setUp() {
        articleRepository = new MemoryArticleRepository();
        article = new Article(null,"작성자", "제목", "본문");
    }

    @AfterEach
    void clear() {
        articleRepository.clear();
    }

    @Test
    @DisplayName("Article을 저장할 수 있다.")
    void saveTest() {
        assertThat(articleRepository.findAll().size()).isEqualTo(0);

        articleRepository.save(article);

        assertThat(articleRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Article을 저장하면 id를 셋팅한다. id는 저장 전 저장소의 사이즈에서 +1한 값이고 id로 article을 조회할 수 있다.")
    void generateId_and_setId_test() {

        assertThat(article.getId()).isNull();
        int targetId = articleRepository.findAll().size()+1;

        articleRepository.save(article);

        Optional<Article> target = articleRepository.findById(targetId);
        target.ifPresent(a-> {
            assertThat(a.getId()).isEqualTo(targetId);
            assertThat(a.getWriter()).isEqualTo("작성자");
            assertThat(a.getTitle()).isEqualTo("제목");
            assertThat(a.getContents()).isEqualTo("본문");
            assertThat(a).isEqualTo(article);
        });
    }

    @Test
    @DisplayName("모든 Article를 조회한다.")
    void findAllTest() {

        articleRepository.save(article);

        assertThat(articleRepository.findAll()).contains(article);

    }

}
