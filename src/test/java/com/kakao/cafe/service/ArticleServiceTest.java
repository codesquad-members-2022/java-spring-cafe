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
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ArticleServiceTest {

    private ArticleService articleService;
    private ArticleRepository articleRepository;

    private Article article;

    @BeforeEach
    void setUp() {
        articleRepository = new MemoryArticleRepository();
        articleService = new ArticleService(articleRepository);

    }

    @Test
    void write_메서드_만약_article이_들어온다면_저장소에_저장한다() {
        ArticleWriteRequest articleWriteRequest = new ArticleWriteRequest("쿠킴", "제목1234", "본문1234");
        article = new ArticleWriteRequest("쿠킴1", "제목1", "본문1").toDomain();
        article.setId(1);

        Article result = articleService.write(articleWriteRequest);

        assertThat(result).isEqualTo(article);
    }

    @Test
    void findAricles_메서드_저장소의_모든_article를_리스트로_리턴한다() {
        articleService.write(new ArticleWriteRequest("쿠킴", "제목1234", "본문1234"));
        articleService.write(new ArticleWriteRequest("쿠킴2", "제목1234", "본문1234"));

        List<Article> result = articleService.findArticles();

        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    void findArticle_메서드_만약_유효한_articleId가_주어진다면_해당_Article를_리턴한다() {
        Article saveArticle = articleService.write(new ArticleWriteRequest("쿠킴", "제목1234", "본문1234"));

        Article result = articleService.findArticle(saveArticle.getId());

        assertThat(result).isEqualTo(saveArticle);
    }

    @Test
    void findArticle_메서드_만약_유효하지않은_articleId가_주어진다면_예외를던진다() {
        assertThatThrownBy(() -> articleService.findArticle(10))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
