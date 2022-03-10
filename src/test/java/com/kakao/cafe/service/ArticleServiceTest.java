package com.kakao.cafe.service;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.domain.ArticleWriteRequest;
import com.kakao.cafe.repository.ArticleRepository;
import com.kakao.cafe.repository.MemoryArticleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ArticleServiceTest {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleRepository articleRepository;

    private Article article;

    @BeforeEach
    void setUp() {
        articleRepository = new MemoryArticleRepository();
        articleService = new ArticleService(articleRepository);
        article = new Article(new ArticleWriteRequest("쿠킴", "제목1234", "본문1234"));
        article.setId(1);
    }

    @Test
    void write_메서드_만약_article이_들어온다면_저장소에_저장한다() {
        ArticleWriteRequest articleWriteRequest = new ArticleWriteRequest("쿠킴", "제목1234", "본문1234");

        Article saveArticle = articleService.write(articleWriteRequest);

        assertThat(saveArticle).isEqualTo(article);
    }

    @Test
    void findAricles_메서드_저장소의_모든_article를_리스트로_리턴한다() {
        articleService.write(new ArticleWriteRequest("쿠킴", "제목1234", "본문1234"));
        articleService.write(new ArticleWriteRequest("쿠킴2", "제목1234", "본문1234"));

        List<Article> article = articleService.findArticles();

        assertThat(article.size()).isEqualTo(2);
    }

}