package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Article;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class JdbcArticleRepository implements ArticleRepository {

    private final NamedParameterJdbcTemplate jdbc;

    public JdbcArticleRepository(DataSource dataSource) {
        jdbc = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Article save(Article article) {
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(article);
        Article articleInformation = findById(article.getId()).orElse(null);

        if (articleInformation == null) {
            jdbc.update("INSERT INTO article (user_id, writer, title, contents, created_date) VALUES (:userId, :writer, :title, :contents, :createdDate)", parameters);
            return article;
        }

        jdbc.update("UPDATE article SET title = :title, contents = :contents, created_date = :createdDate WHERE id = :id", parameters);

        return article;
    }

    @Override
    public Optional<Article> findById(int id) {
        try {
            return Optional.ofNullable(jdbc.queryForObject("SELECT * FROM article WHERE id = :id", Collections.singletonMap("id", id), articleRowMapper()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Article> findAll() {
        return jdbc.query("SELECT * FROM article", articleRowMapper());
    }

    @Override
    public void deleteById(int id) {
        jdbc.update("DELETE FROM article WHERE id = :id", Collections.singletonMap("id", id));
    }

    @Override
    public void deleteAll() {
        jdbc.update("DELETE FROM article", Collections.emptyMap());
        jdbc.update("ALTER TABLE article ALTER COLUMN id RESTART WITH 1", Collections.emptyMap());
    }

    private RowMapper<Article> articleRowMapper() {
        return (rs, rowNum) -> {
            Article article = new Article(rs.getObject("id", Integer.class),
                                          rs.getString("user_id"),
                                          rs.getString("writer"),
                                          rs.getString("title"),
                                          rs.getString("contents"),
                                          rs.getTimestamp("created_date").toLocalDateTime());

            return article;
        };
    }
}
