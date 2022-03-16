package com.kakao.cafe.repository.article;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.repository.CafeRepository;
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
public class JdbcArticleRepository implements CafeRepository<Article, Integer> {

    private final JdbcTemplate jdbcTemplate;

    public JdbcArticleRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Article save(Article article) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("article").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("title", article.getTitle());
        parameters.put("text", article.getText());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        key.intValue();
        return new Article(key.intValue(), article.getTitle(), article.getText());
    }

    @Override
    public Optional<Article> findByName(Integer id) {
        List<Article> result = jdbcTemplate.query("select * from article where id = ?", articleRowMapper(), id);
        return result.stream().findAny();
    }

    @Override
    public List<Article> findAll() {
        return jdbcTemplate.query("select * from article", articleRowMapper());
    }

    private RowMapper<Article> articleRowMapper() {
        return (rs, rowNum) -> new Article(rs.getInt("id"), rs.getString("title"), rs.getString("text"));
    }
}
