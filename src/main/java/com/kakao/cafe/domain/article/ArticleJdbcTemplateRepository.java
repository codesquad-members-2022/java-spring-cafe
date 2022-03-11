package com.kakao.cafe.domain.article;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class ArticleJdbcTemplateRepository implements ArticleRepository {
    private static final Logger log = LoggerFactory.getLogger(ArticleJdbcTemplateRepository.class);
    private final JdbcTemplate jdbcTemplate;

    public ArticleJdbcTemplateRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(Article article) {
        log.info("save request: {}", article);
        jdbcTemplate.update("INSERT INTO article (writer, title, contents) VALUES (?, ?, ?)",
                article.getWriter(), article.getTitle(), article.getContents());
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
