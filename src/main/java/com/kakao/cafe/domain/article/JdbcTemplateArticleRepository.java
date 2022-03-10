package com.kakao.cafe.domain.article;

import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class JdbcTemplateArticleRepository implements ArticleRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcTemplateArticleRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Article article) {
        String sql = "insert into cafe_article (writer, title, contents, writtenTime) values (:writer, :title, :contents, :writtenTime);";
        jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(article));

    }

    @Override
    public Optional<Article> findById(int id) {
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

    }

    private RowMapper<Article> articleRowMapper() {
        return (rs, rowNum) -> new Article(rs.getInt("id"),
                    rs.getString("writer"),
                    rs.getString("title"),
                    rs.getString("contents"),
                    rs.getObject("writtenTime", LocalDateTime.class));
        }
}
