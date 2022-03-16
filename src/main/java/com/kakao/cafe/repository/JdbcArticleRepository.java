package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.domain.User;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.*;

@Primary
@Repository
public class JdbcArticleRepository implements DomainRepository<Article, Integer> {

    private final SimpleJdbcInsert insertJdbc;
    private final NamedParameterJdbcTemplate jdbc;
    private final RowMapper<Article> rowMapper;

    public JdbcArticleRepository(DataSource dataSource) {
        jdbc = new NamedParameterJdbcTemplate(dataSource);
        insertJdbc = new SimpleJdbcInsert(dataSource).withTableName("article").usingGeneratedKeyColumns("id");
        rowMapper = (rs, row) ->
                new Article(
                        rs.getInt("id"),
                        rs.getString("writer"),
                        rs.getString("title"),
                        rs.getString("contents"),
                        rs.getObject("create_date", LocalDate.class)
                );
    }

    @Override
    public List<Article> findAll() {
        return jdbc.query("select id, writer, title, contents, create_date from article",
                Collections.emptyMap(), rowMapper);
    }

    @Override
    public Optional<Article> save(Article article) {
        findById(article.getId()).ifPresentOrElse(
                (other) -> merge(article),
                () -> persist(article)
        );
        return Optional.ofNullable(article);
    }

    private void persist(Article article) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(article);
        article.setId(insertJdbc.executeAndReturnKey(params).intValue());
    }

    private void merge(Article article) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(article);
        jdbc.update("update article set title = :title, contents = :contents where id = :id", params);
    }

    @Override
    public Optional<Article> findById(Integer id) {
        try {
            Article article = jdbc.queryForObject(
                    "select id, writer, title, contents, create_date from article where id = :id",
                    Collections.singletonMap("id", id), rowMapper);

            return Optional.ofNullable(article);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public int deleteById(Integer id) {
        return jdbc.update("delete article where id = :id", Collections.singletonMap("id", id));
    }
}
