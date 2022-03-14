package com.kakao.cafe.repository.article;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.kakao.cafe.domain.article.Article;
import com.kakao.cafe.repository.KeyHolderGenerator;

@Primary
@Repository
public class H2ArticleRepository implements ArticleRepository {

    private final Logger log = LoggerFactory.getLogger(H2ArticleRepository.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final KeyHolderGenerator keyHolderGenerator;

    @Autowired
    public H2ArticleRepository(
        NamedParameterJdbcTemplate jdbcTemplate, KeyHolderGenerator keyHolderGenerator) {
        this.jdbcTemplate = jdbcTemplate;
        this.keyHolderGenerator = keyHolderGenerator;
    }

    @Override
    public Long save(Article article) {
        String sql = "INSERT INTO article (writer, title, contents, writeTime) values (:writer, :title, :contents, :writeTime)";
        KeyHolder keyHolder = keyHolderGenerator.getKeyHolder();
        jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(article), keyHolder, new String[]{"id"});
        long id = Objects.requireNonNull(keyHolder.getKey()).longValue();
        log.info("key is = {}", id);
        return id;
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
        String sql = "DELETE FROM article";
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
