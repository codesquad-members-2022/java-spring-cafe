package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Article;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class JdbcArticleRepository implements ArticleRepository {

    private final NamedParameterJdbcTemplate jdbc;
    private final RowMapper<Article> articleRowMapper = BeanPropertyRowMapper.newInstance(Article.class);
    SimpleJdbcInsert jdbcInsert;

    public JdbcArticleRepository(DataSource dataSource) {
        jdbc = new NamedParameterJdbcTemplate(dataSource);
        jdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("article");
    }

    @Override
    public Article save(Article article) {
        return null;
    }

    @Override
    public Optional<Article> findById(int id) {
        return null;
    }

    @Override
    public List<Article> findAll() {
        return null;
    }

    @Override
    public void clear() {

    }
}
