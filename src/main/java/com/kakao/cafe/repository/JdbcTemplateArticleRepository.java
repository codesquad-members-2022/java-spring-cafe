package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Article;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class JdbcTemplateArticleRepository implements ArticleRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public JdbcTemplateArticleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(Article article) {
        String insertSql = "INSERT INTO ARTICLES (writer, title, contents, created_time) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(insertSql, article.getWriter(), article.getTitle(),
            article.getContents(), currentTime());
    }

    @Override
    public Optional<Article> findById(String articleId) {
        String selectSql = "SELECT * FROM ARTICLES WHERE id=?";
        List<Article> resultArticles = jdbcTemplate.query(selectSql, articleRowMapper(), articleId);
        return resultArticles.stream().findAny();
    }

    @Override
    public List<Article> findAll() {
        String selectSql = "SELECT * FROM ARTICLES";
        return reverseArticleList(jdbcTemplate.query(selectSql, articleRowMapper()));
    }

    private String currentTime() {
        return dateFormat.format(new Date());
    }

    private RowMapper<Article> articleRowMapper() {
        return (rs, rowNum) -> {
            Article article = new Article.ArticleBuilder().setWriter(rs.getString("writer"))
                .setTitle(rs.getString("title"))
                .setContents(rs.getString("contents"))
                .build();
            article.setId(rs.getInt("id"));
            article.setCreatedTime(rs.getString("created_time"));
            return article;
        };
    }

    private List<Article> reverseArticleList(List<Article> articleList) {
        Collections.reverse(articleList);
        return articleList;
    }
}
