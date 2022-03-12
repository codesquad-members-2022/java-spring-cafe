package com.kakao.cafe.domain.article;

import java.text.SimpleDateFormat;
import java.util.*;

public class ArticleMemoryRepository implements ArticleRepository {
    private static final String ARTICLE_CREATED_DATE_PATTERN = "yyyy-MM-dd HH:mm";

    private final List<Article> articleList = new ArrayList<>();
    private final SimpleDateFormat formatter = new SimpleDateFormat(ARTICLE_CREATED_DATE_PATTERN);

    @Override
    public void save(Article article) {
        article.setId(generateId());
        article.setCreatedDate(now());
        articleList.add(article);
    }

    private long generateId() {
        return (articleList.size() + 1);
    }

    private String now() {
        return formatter.format(new Date());
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
