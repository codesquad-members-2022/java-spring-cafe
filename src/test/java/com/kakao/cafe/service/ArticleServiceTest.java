package com.kakao.cafe.service;

import com.kakao.cafe.domain.Article;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ArticleServiceTest {

    @Autowired
    ArticleService articleService;
    private Article article;

    @BeforeEach
    void setUp() {
        article = new Article(0L, "ader", "titleTest", "contentsTest");
    }

    @AfterEach
    void tearDown() {
        articleService.deleteAllArticles();
    }

    @Test
    @DisplayName("Article 정보를 정상적으로 입력하고 저장을 시도하면 정상적으로 저장된다")
    void saveTest() {
        // given

        // when
        Long saveId = articleService.save(article);

        // then
        assertThat(saveId).isEqualTo(article.getId());
    }


    @Test
    @DisplayName("저장되어 있는 id로 검색을 시도하면 해당하는 Article을 리턴한다")
    void findByIdTest() {
        // given
        Long saveId = articleService.save(article);

        // when
        Article findArticle = articleService.findById(saveId);

        // then
        assertThat(findArticle.isSameArticle(saveId)).isEqualTo(true);
    }

    @Test
    @DisplayName("저장되지 않은 id로 검색을 시도하면 NoSuchElementException이 발생한다")
    void findByIdFailTest() {
        // given

        // when
        NoSuchElementException e = assertThrows(NoSuchElementException.class,
                () -> articleService.findById(-1L));

        // then
        assertThat(e.getMessage()).isEqualTo("일치하는 질문이 없습니다.");
    }

    @Test
    @DisplayName("findAll 메소드를 호출하면 현재 저장된 Article리스트를 리턴한다")
    void findAllTest() {
        // given
        Article article2 = new Article(1L, "ader2", "titleTest2", "contentsTest2");
        Article article3 = new Article(2L, "ader3", "titleTest3", "contentsTest3");
        articleService.save(article);
        articleService.save(article2);
        articleService.save(article3);

        // when
        List<Article> findArticles = articleService.findAll();

        // then
        assertThat(findArticles.size()).isEqualTo(3);
    }
}
