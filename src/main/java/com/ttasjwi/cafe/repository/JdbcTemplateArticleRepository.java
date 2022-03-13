package com.ttasjwi.cafe.repository;

import com.ttasjwi.cafe.domain.Article;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcTemplateArticleRepository implements ArticleRepository{

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateArticleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Long save(Article article) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("article").usingGeneratedKeyColumns("article_id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("title", article.getTitle());
        parameters.put("content", article.getContent());
        parameters.put("writer", article.getWriter());
        parameters.put("reg_date_time", article.getRegDateTime());

        Number key = jdbcInsert.executeAndReturnKey(parameters);

        Long articleId = key.longValue();
        article.setArticleId(articleId);
        return articleId;
    }

    @Override
    public Optional<Article> findByArticleId(Long articleId) {
        String sql = "SELECT article_id, title, content, writer, reg_date_time\n" +
                "FROM article\n" +
                "WHERE article_id = ?";

        List<Article> result = jdbcTemplate.query(sql, articleRowMapper(), articleId);
        return result.stream().findAny();
    }

    @Override
    public List<Article> findAll() {
        String sql = "SELECT article_id, title, content, writer, reg_date_time\n" +
                "FROM article";

        return jdbcTemplate.query(sql, articleRowMapper());
    }

    private RowMapper<Article> articleRowMapper() {
        return (rs, rowNum) -> {
            Article article = new Article();
            article.setArticleId(rs.getLong("article_id"));
            article.setTitle(rs.getString("title"));
            article.setContent(rs.getString("content"));
            article.setWriter(rs.getString("writer"));
            article.setRegDateTime(rs.getTimestamp("reg_date_time").toLocalDateTime());
            return article;
        };
    }
}
