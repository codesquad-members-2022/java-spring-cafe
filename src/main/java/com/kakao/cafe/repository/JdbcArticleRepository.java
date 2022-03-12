package com.kakao.cafe.repository;

import static com.kakao.cafe.repository.JdbcArticleRepositorySqls.*;

import com.kakao.cafe.domain.Article;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class JdbcArticleRepository implements ArticleRepository {

    private final NamedParameterJdbcTemplate jdbc;

    public JdbcArticleRepository(DataSource dataSource) {
        jdbc = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Article save(Article article) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("writer", article.getWriter());
        parameters.put("title", article.getTitle());
        parameters.put("contents", article.getContents());
        parameters.put("createdDate", article.getCreatedDate());

        jdbc.update(INSERT_ARTICLE, parameters);

        return article;
    }

    @Override
    public Optional<Article> findById(int id) {
        try {
            Map<String, Integer> parameters = Collections.singletonMap("id", id);
            return Optional.of(jdbc.queryForObject(SELECT_ARTICLE, parameters, articleRowMapper()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Article> findAll() {
        return jdbc.query(SELECT_ALL_ARTICLES, articleRowMapper());
    }

    @Override
    public void clear() {
        jdbc.update(DELETE_ALL_ARTICLES, Collections.emptyMap());
        jdbc.update(INITIAL_COLUMN_NUMBER, Collections.emptyMap());
    }

    private RowMapper<Article> articleRowMapper() {
        return (rs, rowNum) -> {
            Article article = new Article(rs.getInt("id"),
                                          rs.getString("writer"),
                                          rs.getString("title"),
                                          rs.getString("contents"),
                                          rs.getTimestamp("created_date").toLocalDateTime());
            return article;
        };
    }
}
