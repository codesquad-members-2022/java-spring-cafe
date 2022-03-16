package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Article;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ArticleRepository {

    Logger log = LoggerFactory.getLogger(ArticleRepository.class);
    private static final List<Article> articles = new ArrayList<>();

    public Article save(Article article){
        article.setId(articles.size()+1);
        articles.add(article);
        return article;
    }

    public List<Article> findAll(){
        return articles;
    }

    public Article findById(int id){
        return articles.get(id-1);
    }

}
