package com.kakao.cafe.repository.article;

import com.kakao.cafe.domain.article.Article;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ArticleRepositoryTest {

    ArticleRepository repository = new ArticleMemoryRepository();

    @Test
    @DisplayName("아티클 1개를 저장한다.")
    void saveArticle() {
        Article article = new Article("포키", "질문있어요", "오늘 저녁 메뉴는 뭔가요?");

        Article savedResult = repository.save(article);

        assertThat(savedResult).isEqualTo(article);
    }

    @Test
    @DisplayName("아티클이 추가되는 순서대로 id 값이 매겨진다.")
    void articleIncrement() {
        Article article1 = new Article("포키", "질문있어요1", "오늘 저녁 메뉴는 뭔가요?");
        Article article2 = new Article("포키", "질문있어요2", "내일 아침 메뉴는 뭔가요?");
        Article article3 = new Article("포키", "질문있어요3", "내일 점심 메뉴는 뭔가요?");

        Article savedResult1 = repository.save(article1);
        Article savedResult2 = repository.save(article2);
        Article savedResult3 = repository.save(article3);

        assertThat(savedResult1.getId()).isEqualTo(1L);
        assertThat(savedResult2.getId()).isEqualTo(2L);
        assertThat(savedResult3.getId()).isEqualTo(3L);

    }

    @Test
    @DisplayName("id로 아티클 1개를 조회한다.")
    void findArticleById() {
        Article article1 = new Article("포키", "질문있어요1", "오늘 저녁 메뉴는 뭔가요?");
        repository.save(article1);

        Article result = repository.findById(1L).get();

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getWriter()).isEqualTo("포키");
        assertThat(result.getTitle()).isEqualTo("질문있어요1");
    }

    @Test
    @DisplayName("모든 아티클을 조회한다.")
    void findAllArticles() {
        Article article1 = new Article("포키", "질문있어요1", "오늘 저녁 메뉴는 뭔가요?");
        Article article2 = new Article("포키", "질문있어요2", "내일 아침 메뉴는 뭔가요?");
        Article article3 = new Article("포키", "질문있어요3", "내일 점심 메뉴는 뭔가요?");

        Article savedResult1 = repository.save(article1);
        Article savedResult2 = repository.save(article2);
        Article savedResult3 = repository.save(article3);

        List<Article> result = repository.findAll();

        assertThat(result.size()).isEqualTo(3);
    }
}
