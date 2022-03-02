package com.kakao.cafe.domain.article;

import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.*;

@Repository
public class ArticleMemoryRepository implements ArticleRepository {
    private final List<Article> articleList = new ArrayList<>();
    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @Override
    public void save(Article article) {
        article.setId((long) (articleList.size() + 1));
        article.setCreatedDate(formatter.format(new Date()));
        articleList.add(article);
    }

    @Override
    public List<Article> findAllDesc() {
        List<Article> descList = articleList;
        Collections.reverse(descList);
        return descList;
    }

    @Override
    public Optional<Article> findById(Long id) {
        return articleList.stream().filter(m -> m.isTheSameId(id)).findFirst();
    }
}
