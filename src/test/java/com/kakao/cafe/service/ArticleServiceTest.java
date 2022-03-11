package com.kakao.cafe.service;

import com.kakao.cafe.Controller.dto.ArticleForm;
import com.kakao.cafe.Controller.dto.ArticleResponse;
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
    private ArticleForm articleForm;

    @BeforeEach
    void setUp() {
        articleForm = new ArticleForm("ader", "titleTest", "contentsTest");
    }

    @AfterEach
    void tearDown() {
        articleService.deleteAllArticles();
    }

    @Test
    @DisplayName("ArticleForm 정보를 정상적으로 입력하고 저장을 시도하면 정상적으로 저장된다")
    void saveTest() {
        // given

        // when
        Long saveId = articleService.save(articleForm);

        // then
        assertThat(saveId).isEqualTo(articleService.findArticleResponseById(saveId).getId());
    }


    @Test
    @DisplayName("저장되어 있는 id로 검색을 시도하면 해당하는 ArticleResponse를 리턴한다")
    void findByIdTest() {
        // given
        Long saveId = articleService.save(articleForm);

        // when
        ArticleResponse findArticleResponse = articleService.findArticleResponseById(saveId);

        // then
        assertThat(findArticleResponse.getId()).isEqualTo(saveId);
    }

    @Test
    @DisplayName("저장되지 않은 id로 검색을 시도하면 NoSuchElementException이 발생한다")
    void findByIdFailTest() {
        // given

        // when
        NoSuchElementException e = assertThrows(NoSuchElementException.class,
                () -> articleService.findArticleResponseById(-1L));

        // then
        assertThat(e.getMessage()).isEqualTo("일치하는 질문이 없습니다.");
    }

    @Test
    @DisplayName("findAll 메소드를 호출하면 현재 저장된 Article리스트를 리턴한다")
    void findAllTest() {
        // given
        ArticleForm articleForm2 = new ArticleForm("ader2", "titleTest2", "contentsTest2");
        ArticleForm articleForm3 = new ArticleForm("ader3", "titleTest3", "contentsTest3");
        articleService.save(articleForm);
        articleService.save(articleForm2);
        articleService.save(articleForm3);

        // when
        List<ArticleResponse> articles = articleService.findAll();

        // then
        assertThat(articles.size()).isEqualTo(3);
    }
}
