package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcTemplateArticleRepository implements ArticleRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcTemplateArticleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public Article save(Article article) {
        String sql = "INSERT INTO article (writer, title, contents, created_time) VALUES (?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(sql, new String[]{"article_id"});
            ps.setString(1, article.getWriter());
            ps.setString(2, article.getTitle());
            ps.setString(3, article.getContents());
            ps.setDate(4, currentTime());
            return ps;
        }, keyHolder);
        article.setId(keyHolder.getKey().intValue());

        return article;
    }

    private Date currentTime() {
        return new Date(new java.util.Date().getTime());
    }

    @Override
    public List<Article> findAll() {
        String sql = "SELECT article_id, writer, title, contents, created_time FROM article";
        return jdbcTemplate.query(sql, articleRowMapper());
    }

    @Override
    public Optional<Article> findByArticleId(int id) {
        String sql = "SELECT article_id, writer, title, contents, created_time FROM article WHERE article_id = ?";
        List<Article> article = jdbcTemplate.query(sql, articleRowMapper(), id);
        return article.stream().findAny();

    }

    public RowMapper<Article> articleRowMapper() {
        return (rs, rowNum) -> {
            Article article = new Article(
                    rs.getString("writer"),
                    rs.getString("title"),
                    rs.getString("contents"));
            article.setId(rs.getInt("article_id"));
            article.setCreatedTime(rs.getObject("created_time", LocalDateTime.class));
            return article;
        };
    }
}
