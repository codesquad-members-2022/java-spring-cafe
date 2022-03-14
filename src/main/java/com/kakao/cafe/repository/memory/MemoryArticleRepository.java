package com.kakao.cafe.repository.memory;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.repository.ArticleRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MemoryArticleRepository implements ArticleRepository {
    private final List<Article> articles = new ArrayList<>();

    @Override
    public void save(Article article) {
        articles.add(article);
    }

}
