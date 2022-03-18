package com.kakao.cafe.domain.article;

import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@Primary
public class JdbcTemplateArticleRepository implements ArticleRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcTemplateArticleRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Article save(Article article) {
        if(!article.hasId()) {
            return insert(article);
        }
        return update(article);
    }

    @Override
    public Optional<Article> findById(Integer id) {
        String sql = "select id, writer, title, contents, writtenTime from cafe_article where id = :id";
        try {
            Article article = jdbcTemplate.queryForObject(sql, new MapSqlParameterSource("id", id), articleRowMapper());
            return Optional.ofNullable(article);

        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }

    }

    @Override
    public List<Article> findAll() {
        String sql = "select id, writer, title, contents, writtenTime from cafe_article";

        return jdbcTemplate.query(sql, articleRowMapper());
    }

    @Override
    public void clear() {
        String sql = "delete from cafe_article";
        jdbcTemplate.update(sql, new MapSqlParameterSource());
    }

    @Override
    public boolean deleteOne(Integer id) {
        String sql = "delete from cafe_article where id = :id";
        int result = jdbcTemplate.update(sql, new MapSqlParameterSource("id", id));
        if(result == 1) {
            return true;
        }
        return false;
    }

    private RowMapper<Article> articleRowMapper() {
        return (rs, rowNum) -> new Article(rs.getInt("id"),
                    rs.getString("writer"),
                    rs.getString("title"),
                    rs.getString("contents"),
                    rs.getObject("writtenTime", LocalDateTime.class));
        }

    private Article insert(Article article) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "insert into cafe_article (writer, title, contents, writtenTime) values (:writer, :title, :contents, :writtenTime);";
        jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(article), keyHolder);
        article.setId(Objects.requireNonNull(keyHolder.getKey()).intValue());
        return article;
    }

    private Article update(Article article) {
        String sql = "update cafe_article set title = :title, contents = :contents where id = :id";
        jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(article));
        return article;
    }
}
