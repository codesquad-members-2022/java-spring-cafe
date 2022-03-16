package com.ttasjwi.cafe.domain.article;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
class ArticleRepositoryTest {

    @Autowired
    private ArticleRepository articleRepository;

    @Test
    @DisplayName("저장 게시글의 인덱스로 조회 -> 같은 저장게시글 반환")
    void saveTest() {
        // given
        Article article = Article.builder()
                .title("자바 두명 타요")
                .content("활활 잘 탄다~")
                .writer("사용자")
                .regDateTime(LocalDateTime.now())
                .build();

        articleRepository.save(article);
        Long articleId = article.getArticleId();

        // when
        Article findArticle = articleRepository.findByArticleId(articleId).get();

        // then
        assertThat(article).isEqualTo(findArticle);
    }

}