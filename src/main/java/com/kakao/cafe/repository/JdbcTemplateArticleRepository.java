package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Article;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class JdbcTemplateArticleRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateArticleRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Article save(Article article){
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("article").usingGeneratedKeyColumns("id");
        article.setCreatedDate(LocalDateTime.now());

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("title", article.getTitle());
        parameters.put("writer", article.getWriter());
        parameters.put("content", article.getContent());
        parameters.put("date", article.getCreatedDate());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        article.setId((int) key.longValue());
        return article;
    }

    public List<Article> findAll(){
        return jdbcTemplate.query("select * from article", articleRowMapper());
    }

    public Optional<Article> findById(int id){
        List<Article> result = jdbcTemplate.query("select * from article where id = ?", articleRowMapper(), id);
        return result.stream().findAny();
    }

    private RowMapper<Article> articleRowMapper(){
        return (resultSet, Article) -> {
            Article article = new Article();
            article.setId((int) resultSet.getLong("id"));
            article.setTitle(resultSet.getString("title"));
            article.setWriter(resultSet.getString("writer"));
            article.setContent(resultSet.getString("content"));
            article.setCreatedDate(resultSet.getTimestamp("date").toLocalDateTime());
            return article;
        };
    }
}
