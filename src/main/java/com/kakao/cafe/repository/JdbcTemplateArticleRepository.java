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
public class JdbcTemplateArticleRepository implements ArticleRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateArticleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void createArticle(Article article) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("article").usingGeneratedKeyColumns("articleIdx");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("articleTitle", article.getArticleTitle());
        parameters.put("articleContent", article.getArticleContent());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        article.setArticleIdx(key.longValue());
    }

    @Override
    public List<Article> findAllArticle() {
        String sql = "Select articleIdx, articleTitle, articleContent From article";

        return jdbcTemplate.query(sql, articleRowMapper());
    }

    @Override
    public Optional<Article> findArticle(long articleIdx) {
        String sql = "Select articleIdx, articleTitle, articleContent From article Where articleIdx = ?";

        Article article = jdbcTemplate.queryForObject(sql, articleRowMapper(), articleIdx);

        return Optional.ofNullable(article);
    }

    private RowMapper<Article> articleRowMapper(){
        return (resultSet, rowNum) -> {
            Article article = new Article();
            article.setArticleIdx(resultSet.getLong("articleIdx"));
            article.setArticleTitle(resultSet.getString("articleTitle"));
            article.setArticleContent(resultSet.getString("articleContent"));

            return article;
        };
    }
}
