package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Article;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class MemoryArticleRepository implements ArticleRepository {

    private static final List<Article> store = Collections.synchronizedList(new ArrayList<>());

    @Override
    public int save(Article article) {
        store.add(article);
        return store.size() - 1;
    }

    public void clear() {
        store.clear();
    }
}
