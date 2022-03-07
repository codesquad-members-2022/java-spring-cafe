package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Article;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MemoryArticleRepository implements ArticleRepository {

    private final List<Article> articleList = new ArrayList<>();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");

    @Override
    public void save(Article article) {
        article.setCreatedTime(currentTime());
        articleList.add(article);
    }

    private String currentTime() {
        return dateFormat.format(new Date());
    }
}
