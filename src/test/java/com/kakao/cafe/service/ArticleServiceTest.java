package com.kakao.cafe.service;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.domain.dto.ArticleForm;
import com.kakao.cafe.repository.ArticleRepository;
import com.kakao.cafe.repository.MemoryArticleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class ArticleServiceTest {
    MemoryArticleRepository articleRepository;
    ArticleService articleService;

    @BeforeEach
    public void beforeEach() {
        articleRepository = new MemoryArticleRepository();
        articleService = new ArticleService(articleRepository);
    }

    @AfterEach
    public void afterEach() {
        articleRepository.clearStore();
    }

    @Test
    @DisplayName("게시글이 잘 저장되는가")
    void post() {
        ArticleForm articleForm1 = new ArticleForm("게시글 제목1", "나단", "첫번째 게시글입니다.");
        articleService.post(articleForm1);
        ArticleForm savedArticleForm = articleService.findOneArticle(0);

        assertThat("게시글 제목1").isEqualTo(savedArticleForm.getTitle());
    }

    @Test
    @DisplayName("모든 게시글이 잘 가져와지는가")
    void findArticles() {
        ArticleForm articleForm1 = new ArticleForm("게시글 제목1", "나단", "첫번째 게시글입니다.");
        ArticleForm articleForm2 = new ArticleForm("게시글 제목2", "호눅스", "두번째 게시글입니다.");
        ArticleForm articleForm3 = new ArticleForm("게시글 제목3", "크롱", "세번째 게시글입니다.");
        articleService.post(articleForm1);
        articleService.post(articleForm2);
        articleService.post(articleForm3);

        List<Article> articles = articleService.findArticles();

        assertThat(articles.size()).isEqualTo(3);
        assertThat("게시글 제목1").isEqualTo(articles.get(0).getTitle());
        assertThat("게시글 제목2").isEqualTo(articles.get(1).getTitle());
        assertThat("게시글 제목3").isEqualTo(articles.get(2).getTitle());
    }

    @Test
    @DisplayName("인덱스에 해당하는 ArticleForm이 잘 가져와지는가")
    void findOneArticle() {
        ArticleForm articleForm1 = new ArticleForm("게시글 제목1", "나단", "첫번째 게시글입니다.");
        ArticleForm articleForm2 = new ArticleForm("게시글 제목2", "호눅스", "두번째 게시글입니다.");
        articleService.post(articleForm1);
        articleService.post(articleForm2);

        ArticleForm oneArticle = articleService.findOneArticle(1);
        assertThat("게시글 제목2").isEqualTo(oneArticle.getTitle());
    }
    @Test
    @DisplayName("인덱스가 초과되면, IndexOutOfBoundsException이 뜨는가")
    void findOneArticleOverIndexBound() {
        ArticleForm articleForm1 = new ArticleForm("게시글 제목1", "나단", "첫번째 게시글입니다.");
        ArticleForm articleForm2 = new ArticleForm("게시글 제목2", "호눅스", "두번째 게시글입니다.");
        articleService.post(articleForm1);
        articleService.post(articleForm2);

        assertThatThrownBy(()->articleService.findOneArticle(2))
                .isInstanceOf(IndexOutOfBoundsException.class);
    }
}
