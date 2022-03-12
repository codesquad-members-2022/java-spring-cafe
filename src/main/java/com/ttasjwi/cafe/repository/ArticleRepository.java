package com.ttasjwi.cafe.repository;

import com.ttasjwi.cafe.domain.Article;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@Repository
public class ArticleRepository {

    private final List<Article> storage = Collections.synchronizedList(new ArrayList<>());

    /**
     * @param article : 게시글 객체
     * @return storage.indexOf(article) + 1 : 게시글의 저장인덱스 + 1 을 아이디로 삼음
     */
    public int save(Article article) {
        storage.add(article);
        return storage.indexOf(article) + 1;
    }

    public Article findByArticleId(int articleId) {
        validateArticleId(articleId);
        return storage.get(articleId-1);
    }

    private void validateArticleId(int articleId) {
        if (isValidArticleId(articleId)) {
            return;
        }
        throw new NoSuchElementException("유효하지 않은 게시글 인덱스입니다.");
    }

    private boolean isValidArticleId(int articleId) {
        return 1 <= articleId && articleId <= storage.size();
    }

    public List<Article> findAll() {
        return Collections.unmodifiableList(storage);
    }

}
