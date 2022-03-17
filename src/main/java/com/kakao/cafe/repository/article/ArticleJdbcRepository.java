package com.kakao.cafe.repository.article;

import com.kakao.cafe.domain.article.Article;
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
public class ArticleJdbcRepository implements ArticleRepository {

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;

    public ArticleJdbcRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        this.jdbcInsert.withTableName("articles").usingGeneratedKeyColumns("id");

    }

    @Override
    public Article save(Article article) {
        Map<String, Object> params = new HashMap<>();
        params.put("writer", article.getWriter());
        params.put("title", article.getTitle());
        params.put("contents", article.getContents());

        int id = jdbcInsert.executeAndReturnKey(params).intValue();
        article.setId(id);
        return article;
    }

    @Override
    public Optional<Article> findById(Integer id) {
        List<Article> result = jdbcTemplate.query("select id, writer, title, contents from articles where id = ?", articleRowMapper(),id);
        return result.stream().findAny();
    }

    @Override
    public List<Article> findAll() {
        return jdbcTemplate.query("select id, writer, title, contents from articles", articleRowMapper());
    }

    private RowMapper<Article> articleRowMapper() {
        return (rs, rowNum) -> {
            Article article = new Article(
                    rs.getString("writer"),
                    rs.getString("title"),
                    rs.getString("contents"));
            article.setId(rs.getInt("id"));
            return article;
        };
    }
}
