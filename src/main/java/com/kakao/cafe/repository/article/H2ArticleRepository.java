package com.kakao.cafe.repository.article;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.kakao.cafe.domain.article.Article;

@Primary
@Repository
public class H2ArticleRepository implements ArticleRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public H2ArticleRepository(
        NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Article article) {
        String sql = "INSERT INTO article (writer, title, contents, writeTime) values (:writer, :title, :contents, :writeTime)";
        jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(article));
    }

    @Override
    public List<Article> findAll() {
        String sql = "SELECT * FROM article";
        return jdbcTemplate.query(sql, makeArticle());
    }

    @Override
    public Optional<Article> findById(Long id) {
        String sql = "SELECT * FROM article WHERE id = :article_id";
        SqlParameterSource namedParameter = new MapSqlParameterSource("article_id", id);
        Article article = jdbcTemplate.queryForObject(sql, namedParameter, makeArticle());
        return Optional.ofNullable(article);
    }

    @Override
    public void deleteAll() {
        String sql = "DELET FROM article";
        jdbcTemplate.update(sql, new MapSqlParameterSource());
    }

    private RowMapper<Article> makeArticle() {
        return (resultSet, rowNum) -> {
            Article articleTmp = new Article(
                resultSet.getString("writer"),
                resultSet.getString("title"),
                resultSet.getString("contents"));
            articleTmp.setId(resultSet.getLong("id"));
            articleTmp.writeWhenCreated(resultSet.getObject("writeTime", LocalDateTime.class));
            return articleTmp;
        };
    }
}
