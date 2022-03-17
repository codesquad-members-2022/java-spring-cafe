package com.kakao.cafe.repository;

import com.kakao.cafe.entity.Article;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.sql.DataSource;

@Repository
public class ArticleJdbcRepository implements ArticleRepository {

    private final NamedParameterJdbcOperations jdbc;
    private final KeyHolder keyHolder;

    public ArticleJdbcRepository(DataSource dataSource) {
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
        this.keyHolder = new GeneratedKeyHolder();
    }

    @Override
    public Article articleSave(Article article) {
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(article);
        String sql = "insert into `article` (title, writer, contents, date) values (:title, :writer, :contents, :date)";
        jdbc.update(sql, params, keyHolder);
        article.setDate(LocalDateTime.now());
        article.setId(Objects.requireNonNull(keyHolder.getKey()).intValue());
        return article;
    }

    @Override
    public List<Article> findAllArticle() {
        String sql = "select id, title, writer, contents, date from `article` order by date desc";
        List<Article> targets = jdbc.query(sql, articleRowMapper());
        return Collections.unmodifiableList(targets);
    }

    @Override
    public Optional<Article> findArticleById(int articleId) {
        try {
            String sql = "select id, title, writer, contents, date from `article` where id = :id";
            Map<String, Object> params = Collections.singletonMap("id", articleId);
            Article target = jdbc.queryForObject(sql, params, articleRowMapper());
            return Optional.ofNullable(target);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private RowMapper<Article> articleRowMapper() {
        return (rs, rowNum) -> {
            Article article = new Article(
                    rs.getString("writer"),
                    rs.getString("title"),
                    rs.getString("contents"));
            article.setId(rs.getInt("id"));
            article.setDate(rs.getTimestamp("date").toLocalDateTime());
            return article;
        };
    }
}
