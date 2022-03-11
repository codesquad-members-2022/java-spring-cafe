package com.kakao.cafe.repository.article;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kakao.cafe.domain.article.Article;

@Repository
public class H2ArticleRepository implements ArticleRepository {

    private final JdbcTemplate jdbcTemplate;
    //
    public H2ArticleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Article> findAll() {
        return null;
    }

    @Override
    public void save(Article article) {

    }

    @Override
    public Optional<Article> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void deleteAll() {

    }
}
