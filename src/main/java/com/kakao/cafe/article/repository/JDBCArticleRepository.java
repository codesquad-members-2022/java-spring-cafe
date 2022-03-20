package com.kakao.cafe.article.repository;

import com.kakao.cafe.article.domain.Article;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class JDBCArticleRepository implements ArticleRepository {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedJdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public JDBCArticleRepository(DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
        this.namedJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
    }

    @Override
    public Optional<Article> save(Article article) {
        if (article.getId() == null) {
            return createArticle(article);
        }

        return updateArticle(article);
    }

    @Override
    public Optional<Article> findById(Long savedId) {
        String findByIdSql = "SELECT id, title, content, view_count, created_date, modified_date FROM article WHERE id=?";
        List<Article> articles = jdbcTemplate.query(findByIdSql, articleRowMapper(), savedId);

        if (articles.size() > 0) {
            return Optional.of(articles.get(0));
        }

        return Optional.empty();
    }

    @Override
    public Optional<List<Article>> findAll() {
        String findAllSql = "SELECT id, title, content, view_count, created_date, modified_date FROM article";
        List<Article> articles = jdbcTemplate.query(findAllSql, articleRowMapper());

        if (articles.size() > 0) {
            return Optional.of(articles);
        }

        return Optional.of(Collections.emptyList());
    }

    @Override
    public void deleteAll() {
        String deleteAllSql = "DELETE FROM article";
        jdbcTemplate.update(deleteAllSql);
    }

    private Optional<Article> createArticle(Article article) {
        try {
            if (!simpleJdbcInsert.isCompiled()) {
                simpleJdbcInsert.withTableName("article").usingGeneratedKeyColumns("id");
            }
            Number number = simpleJdbcInsert.executeAndReturnKey(new BeanPropertySqlParameterSource(article));

            article.setId(number.longValue());

            return Optional.of(article);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    private Optional<Article> updateArticle(Article article) {
        article.updateModifiedDate();

        String updateSql = "UPDATE article SET " +
                " title = :title ," +
                " content = :content ," +
                " view_count = :viewCount ," +
                " modified_date = :modifiedDate " +
                " WHERE id = :id";
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("title", article.getTitle())
                .addValue("content", article.getContent())
                .addValue("viewCount", article.getViewCount())
                .addValue("modifiedDate", article.getModifiedDate())
                .addValue("id", article.getId());

        int changedRowCount = namedJdbcTemplate.update(updateSql, parameterSource);

        if (changedRowCount > 0) {
            return Optional.of(article);
        }

        return Optional.empty();
    }

    private RowMapper<Article> articleRowMapper() {
        return (rs, rowNum) -> new Article(
                rs.getLong("id"),
                rs.getString("title"),
                rs.getString("content"),
                rs.getTimestamp("created_date").toLocalDateTime(),
                rs.getTimestamp("modified_date").toLocalDateTime(),
                rs.getLong("view_count")
        );
    }
}
