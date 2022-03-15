package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Article;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
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
    public Article save(Article article) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("article").usingGeneratedKeyColumns("index"); // pk column 지정 및 auto increment
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("title", article.getTitle());
        parameters.put("writer", article.getWriter());
        parameters.put("contents", article.getContents());
        parameters.put("datetime", article.getDateTime());
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        article.setIndex(key.intValue());
        return article;
    }

    @Override
    public Optional<Article> findByIndex(int index) {
        List<Article> articles = jdbcTemplate.query("select * from article where index = ?", articleRowMapper(), index);
        return articles.stream().findAny();
    }

    @Override
    public List<Article> findAll() {
        return jdbcTemplate.query("select * from article", articleRowMapper());
    }

    private RowMapper<Article> articleRowMapper() {
        return (rs, rowNum) -> {
            Article article = new Article(
                    rs.getString("title"),
                    rs.getString("writer"),
                    rs.getString("contents")
            );
            article.setIndex(rs.getInt("index"));
            return article;
        };
    }

}
