package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.domain.ArticleWriteRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryArticleRepositoryTest {

    MemoryArticleRepository repository;

    Article article;

    @BeforeEach
    void setUp() {
        repository = new MemoryArticleRepository();
        article = new Article("쿠킴", "제목1234", "본문1234");
    }

    @AfterEach
    void tearDown() {
        repository.clear();
    }

    @Test
    void save메소드는_만약_article_객체가_주어진다면_article_객체를_저장한다() {
        Article result = repository.save(article);

        assertThat(result.getWriter()).isEqualTo("쿠킴");
        assertThat(result.getTitle()).isEqualTo("제목1234");
        assertThat(result.getContents()).isEqualTo("본문1234");
    }

    @Test
    void findAll메소드는_만약_파라미터없이_실행된다면_저장되어있는_article_모두를_리스트로_리턴한다() {
        repository.save(new Article("쿠킴1", "제목1", "본문1"));
        repository.save(new Article("쿠킴2", "제목1234", "본문1234"));

        List<Article> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    void findByArticleId메소드는_만약_존재하는_id가주어진다면_해당id의_옵셔널_Article를_리턴한다() {
        Article article = repository.save(new Article("쿠킴1", "제목1", "본문1"));

        Optional<Article> result = repository.findByArticleId(1);

        assertThat(result).contains(article);
    }

    @Test
    void findByArticleId메소드는_만약_존재하지않는_id가주어진다면_empty옵셔널을_리턴한다() {
        Optional<Article> result = repository.findByArticleId(1);

        assertThat(result.isEmpty()).isTrue();
    }
}
