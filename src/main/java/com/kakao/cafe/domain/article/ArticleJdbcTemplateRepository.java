package com.kakao.cafe.domain.article;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class ArticleJdbcTemplateRepository implements ArticleRepository {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ArticleJdbcTemplateRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void save(Article article) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", article.getId());
        params.addValue("writer", article.getWriter());
        params.addValue("title", article.getTitle());
        params.addValue("contents", article.getContents());
        if (isAlreadyWritten(article)) {
            namedParameterJdbcTemplate.update("UPDATE article SET title = :title, contents = :contents WHERE id = :id", params);
            return;
        }
        namedParameterJdbcTemplate.update("INSERT INTO article (writer, title, contents) VALUES (:writer, :title, :contents)",
                params);
    }

    private boolean isAlreadyWritten(Article article) {
        return findById(article.getId()).isPresent();
    }

    @Override
    public List<Article> findAllDesc() {
        String query = "SELECT * FROM article ORDER BY id DESC";
        return jdbcTemplate.query(query, articleRowMapper());
    }

    @Override
    public Optional<Article> findById(Long id) {
        String query = "select * from article where id = " + id;
        List<Article> result = jdbcTemplate.query(query, articleRowMapper());
        return result.stream().findAny();
    }

    private RowMapper<Article> articleRowMapper() {
        return (rs, rowNum) -> {
            long id = rs.getLong("id");
            String writer = rs.getString("writer");
            String title = rs.getString("title");
            String contents = rs.getString("contents");
            String createdDate = rs.getTimestamp("created_date").toString();
            return new Article(id, writer, title, contents, createdDate);
        };
    }
}
