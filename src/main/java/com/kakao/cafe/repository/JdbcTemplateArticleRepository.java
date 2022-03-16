package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Article;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public class JdbcTemplateArticleRepository implements ArticleRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");


    public JdbcTemplateArticleRepository(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        jdbcInsert = new SimpleJdbcInsert(dataSource)
            .withTableName("ARTICLES")
            .usingGeneratedKeyColumns("id");
    }

    @Override
    public void save(Article article) {
        article.setCreatedTime(currentTime());
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(
            article);
        jdbcInsert.execute(sqlParameterSource);
    }

    @Override
    public Optional<Article> findById(String articleId) {
        String selectSql = "SELECT * FROM ARTICLES WHERE id=:articleId";
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
            .addValue("articleId", articleId);
        List<Article> resultArticles = jdbcTemplate.query(selectSql, sqlParameterSource,
            articleRowMapper());
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
