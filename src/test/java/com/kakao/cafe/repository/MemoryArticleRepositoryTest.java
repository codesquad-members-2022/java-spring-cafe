package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Article;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class MemoryArticleRepositoryTest {
    MemoryArticleRepository repository = new MemoryArticleRepository();

    @Test
    @DisplayName("글 작성시 글이 저장되는지 확인한다")
    public void save() {
        Article article = new Article(
                "케이",
                "테스트",
                "테스트입니다.");

        assertThat(article.getWriter()).isEqualTo("케이");
        assertThat(article.getTitle()).isEqualTo("테스트");
        assertThat(article.getContent()).isEqualTo("테스트입니다.");

    }

}
