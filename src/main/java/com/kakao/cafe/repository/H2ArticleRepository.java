package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.entity.ArticleEntity;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.*;
import java.util.stream.Collectors;

@Primary
@org.springframework.stereotype.Repository
public class H2ArticleRepository implements Repository<Article, Long> {

    private SimpleJdbcInsert insertJdbc;
    private NamedParameterJdbcTemplate jdbc;
    private RowMapper<ArticleEntity> rowMapper = BeanPropertyRowMapper.newInstance(ArticleEntity.class);

    public H2ArticleRepository(DataSource dataSource) {
        jdbc = new NamedParameterJdbcTemplate(dataSource);
        insertJdbc = new SimpleJdbcInsert(dataSource).withTableName("article").usingGeneratedKeyColumns("id");
    }

    @Override
    public List<Article> findAll() {
        List<ArticleEntity> entities = jdbc.query("select id, writer, title, contents, create_date from article",
                Collections.emptyMap(), rowMapper);

        return new Vector<>(entities.stream().map(entity -> entity.convertToArticle()).collect(Collectors.toList()));
    }

    @Override
    public Optional<Article> save(Article article) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(ArticleEntity.of(article));
        article.setId(insertJdbc.executeAndReturnKey(params).longValue());
        return Optional.ofNullable(article);
    }

    @Override
    public Optional<Article> findOne(Long id) {
        try {
            Map<String, ?> params = Collections.singletonMap("id", id);
            ArticleEntity entity = jdbc.queryForObject(
                    "select id, writer, title, contents, create_date from article where id = :id", params, rowMapper);

            return Optional.ofNullable(entity.convertToArticle());
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void clear() {

    }
}
