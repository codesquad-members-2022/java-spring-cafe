package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Article;
import org.springframework.jdbc.core.JdbcTemplate;
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
public class JdbcArticleRepository implements ArticleRepository{

    private final JdbcTemplate jdbcTemplate;

    public JdbcArticleRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Article saveArticle(Article article) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("article").usingGeneratedKeyColumns("id");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("title", article.getTitle());
        parameters.put("writer", article.getWriter());
        parameters.put("contents", article.getContent());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        article.setId(key.longValue());
        return article;
    }

    @Override
    public List<Article> loadAllArticles() {
        return jdbcTemplate.query("select * from article", articleRowMapper());
    }

    @Override
    public Optional<Article> loadOneArticle(Long index) {
        List<Article> result = jdbcTemplate.query("select * from article where id = ?", articleRowMapper(), index);
        return result.stream().findAny();
    }

    private RowMapper<Article> articleRowMapper(){
        return(rs, rowNum) -> {
            Article article = new Article(
                    rs.getString("writer"),
                    rs.getString("title"),
                    rs.getString("contents"));
            article.setId(rs.getLong("id"));
            return article;
        };
    }
}
