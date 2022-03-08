package com.kakao.cafe.repository.jdbc;

import com.kakao.cafe.config.QueryProps;
import com.kakao.cafe.domain.Article;
import com.kakao.cafe.domain.Article.Builder;
import com.kakao.cafe.repository.ArticleRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ArticleJdbcRepository implements ArticleRepository {

    // 데이터베이스 내 필드명
    private static final String ARTICLE_ID = "article_id";
    private static final String WRITER = "writer";
    private static final String TITLE = "title";
    private static final String CONTENTS = "contents";
    private static final String CREATED_DATE = "created_date";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final KeyHolderFactory keyHolderFactory;
    private final QueryProps queryProps;

    public ArticleJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate,
        KeyHolderFactory keyHolderFactory, QueryProps queryProps) {
        this.jdbcTemplate = jdbcTemplate;
        this.keyHolderFactory = keyHolderFactory;
        this.queryProps = queryProps;
    }

    @Override
    public Article save(Article article) {
        String sql = queryProps.get(Query.INSERT_ARTICLE);
        KeyHolder keyHolder = keyHolderFactory.newKeyHolder();

        article.setCreatedDate(LocalDateTime.now());
        jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(article), keyHolder);

        if (keyHolder.getKey() != null) {
            article.setArticleId(keyHolder.getKey().intValue());
        }
        return article;
    }

    @Override
    public List<Article> findAll() {
        String sql = queryProps.get(Query.SELECT_ARTICLES);

        return jdbcTemplate.query(sql,
            (rs, rowNum) ->
                new Article.Builder()
                    .articleId(rs.getInt(ARTICLE_ID))
                    .writer(rs.getString(WRITER))
                    .title(rs.getString(TITLE))
                    .contents(rs.getString(CONTENTS))
                    .createdDate(rs.getObject(CREATED_DATE, LocalDateTime.class))
                    .build()
        );
    }

    @Override
    public Optional<Article> findById(Integer articleId) {
        String sql = queryProps.get(Query.SELECT_ARTICLE);

        SqlParameterSource namedParameter = new MapSqlParameterSource()
            .addValue(ARTICLE_ID, articleId);

        try {
            Article article = jdbcTemplate.queryForObject(sql, namedParameter,
                (rs, rowNum) ->
                    new Builder()
                        .articleId(rs.getInt(ARTICLE_ID))
                        .writer(rs.getString(WRITER))
                        .title(rs.getString(TITLE))
                        .contents(rs.getString(CONTENTS))
                        .createdDate(rs.getObject(CREATED_DATE, LocalDateTime.class))
                        .build()
            );
            return Optional.ofNullable(article);

        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }

    }

    @Override
    public void deleteAll() {
        String sql = queryProps.get(Query.DELETE_ARTICLES);

        jdbcTemplate.update(sql, new MapSqlParameterSource());
    }
}
