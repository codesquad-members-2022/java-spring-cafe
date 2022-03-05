package com.kakao.cafe.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.kakao.cafe.entity.Article;
import com.kakao.cafe.entity.User;
import com.kakao.cafe.repository.ArticleMemorySaveRepository;
import com.kakao.cafe.repository.UserMemorySaveRepository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ArticleServiceTest {

    private ArticleService articleService;
    private UserMemorySaveRepository userRepository;
    private ArticleMemorySaveRepository articleRepository;
    private Article articleA, articleB;
    private User user;

    @BeforeEach
    void SetUp() {
        this.userRepository = new UserMemorySaveRepository();
        this.articleRepository = new ArticleMemorySaveRepository();
        this.articleService = new ArticleService(articleRepository, userRepository);
        this.articleA = new Article("writerA", "titleA", "contentsA");
        this.articleB = new Article("writerA", "titleB", "contentsB");
        this.user = new User("email", "writerA", "name", "pwd");
    }

    @AfterEach
    void SetDown() {
        userRepository.clearStore();
        articleRepository.clearStore();
    }

    @Test
    @DisplayName("등록 되어 있는 유저가 글을 작성하는 경우 해당 글의 제목을 반환해야 한다.")
    void generateArticle() {
        // given
        String actualTitle = "titleA";
        userRepository.userSave(user);
        // when
        String expectedTitle = articleService.generateArticle(articleA);
        // then
        Assertions.assertThat(actualTitle).isEqualTo(expectedTitle);
    }

    @Test
    @DisplayName("등록 되지 않은 유저가 글을 작성하는 경우 예외가 발생해야 한다.")
    void generateArticleException() {
        // then
        assertThatThrownBy(() -> {
            articleService.generateArticle(articleA);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("등록되지 않은 유저는 글을 작성할 수 없습니다.");
    }

    @Test
    @DisplayName("존재하는 Article ID로 게시물을 검색하는 경우 해당 게시물을 반환해야 한다.")
    void findArticleById() {
        // given
        int articleIdA = 1;
        userRepository.userSave(user);
        Article[] articles = {articleA, articleB};
        for (var article : articles) {
            articleService.generateArticle(article);
        }
        // when
        Article article = articleService.findArticleById(articleIdA);
        // then
        assertThat(articleA).isEqualTo(article);
    }

    @Test
    @DisplayName("존재하지 않는 Article ID로 게시물을 검색하는 경우 예외가 발생해야 한다.")
    void findArticleByIdException() {
        // given
        userRepository.userSave(user);
        Article[] articles = {articleA, articleB};
        for (var article : articles) {
            articleService.generateArticle(article);
        }
        // when
        int articleId = 3;
        // then
        assertThatThrownBy(() -> {
            articleService.findArticleById(articleId);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("존재하지 않는 게시물 번호입니다.");
    }
}
