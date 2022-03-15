package com.kakao.cafe.repository.article;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.repository.CafeRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class MemoryArticleRepository implements CafeRepository<Article, Integer> {

    private List<Article> articles = new CopyOnWriteArrayList<>();

    @Override
    public void save(Article article) {
        Article articleWithId = new Article (articles.size() + 1, article.getTitle(), article.getText());
        articles.add(articleWithId);
    }

    @Override
    public Optional<Article> findByName(Integer id) {
        return articles.stream()
                .filter(article -> article.getId() == id)
                .findAny();
    }

    @Override
    public List<Article> findAll() {
        return new ArrayList<>(List.copyOf(articles));
    }

    public void clearStore() {
        articles.clear();
    }
}
