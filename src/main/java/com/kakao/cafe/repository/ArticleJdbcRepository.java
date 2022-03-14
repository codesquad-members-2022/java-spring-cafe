package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Article;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.*;

@Repository
@Primary
public class ArticleJdbcRepository implements ArticleRepository{

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ArticleJdbcRepository(DataSource dataSource) {
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Article save(Article article) {
        String sql = "INSERT INTO article (writer, title, contents, created_time, updated_time) " +
                "VALUES (:writer, :title, :contents, :created_time, :updated_time)";

        namedParameterJdbcTemplate.update(sql, generateParams(article));

        return findByWriterAndTitle(article.getWriter(), article.getTitle()).stream()
                .findAny().orElse(article);
    }

    private Map<String, Object> generateParams(Article article) {
        Map<String, Object> params = new HashMap<>();

        params.put("writer", article.getWriter());
        params.put("title", article.getTitle());
        params.put("contents", article.getContents());
        params.put("created_time", article.getCreatedTime());
        params.put("updated_time", article.getUpdatedTime());

        return params;
    }

    @Override
    public Optional<Article> findById(Long id) {
        String sql = "SELECT id, writer, title, contents, created_time, updated_time " +
                     "FROM article " +
                     "WHERE id = :id";

        Map<String, Object> params = new HashMap<>();
        params.put("id", id);

        try {
            return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(sql, params, articleRowMapper()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Article> findAll() {
        return namedParameterJdbcTemplate.query(
                "SELECT id, writer, title, contents, created_time, updated_time " +
                        "FROM article",
                Collections.emptyMap(),
                articleRowMapper());
    }

    private List<Article> findByWriterAndTitle(String writer, String title) {
        Map<String, Object> params = new HashMap<>();
        params.put("writer", writer);
        params.put("title", title);

        return namedParameterJdbcTemplate.query(
                "SELECT id, writer, title, contents, created_time, updated_time " +
                    "FROM article " +
                    "WHERE writer = :writer AND title = :title ",
                params,
                articleRowMapper());
    }

    private RowMapper<Article> articleRowMapper() {
        return (rs, rowNum) ->
                new Article(
                        rs.getLong("id"),
                        rs.getString("writer"),
                        rs.getString("title"),
                        rs.getString("contents"),
                        rs.getObject("created_time", LocalDateTime.class),
                        rs.getObject("updated_time", LocalDateTime.class)
                );
    }
}
