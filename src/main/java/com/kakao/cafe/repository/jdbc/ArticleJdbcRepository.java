package com.kakao.cafe.repository.jdbc;

import com.kakao.cafe.config.QueryProps;
import com.kakao.cafe.domain.Article;
import com.kakao.cafe.repository.ArticleRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ArticleJdbcRepository implements ArticleRepository {

    // 데이터베이스 내 필드명
    private static final String ARTICLE_ID_CAMEL = "articleId";
    private static final String ARTICLE_ID_SNAKE = "article_id";
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
        if (article.getArticleId() == null) {
            // persist
            String sql = queryProps.get(Query.INSERT_ARTICLE);
            KeyHolder keyHolder = keyHolderFactory.newKeyHolder();

            article.setCreatedDate(LocalDateTime.now());
            jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(article), keyHolder);

            if (keyHolder.getKey() != null) {
                article.setArticleId(keyHolder.getKey().intValue());
            }
            return article;
        }
        // merge
        String sql = queryProps.get(Query.UPDATE_ARTICLE);
        jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(article));

        return article;
    }

    @Override
    public List<Article> findAll() {
        String sql = queryProps.get(Query.SELECT_ARTICLES);

        return jdbcTemplate.query(sql,
            (rs, rowNum) ->
                new Article(
                    rs.getInt(ARTICLE_ID_SNAKE),
                    rs.getString(WRITER),
                    rs.getString(TITLE),
                    rs.getString(CONTENTS),
                    rs.getObject(CREATED_DATE, LocalDateTime.class)
                )
        );
    }

    @Override
    public Optional<Article> findById(Integer articleId) {
        String sql = queryProps.get(Query.SELECT_ARTICLE);

        try {
            Article article = jdbcTemplate.queryForObject(sql,
                new MapSqlParameterSource()
                    .addValue(ARTICLE_ID_CAMEL, articleId),
                (rs, rowNum) ->
                    new Article(
                        rs.getInt(ARTICLE_ID_SNAKE),
                        rs.getString(WRITER),
                        rs.getString(TITLE),
                        rs.getString(CONTENTS),
                        rs.getObject(CREATED_DATE, LocalDateTime.class)
                    )
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

    @Override
    public void deleteById(Integer articleId) {
        String sql = queryProps.get(Query.DELETE_ARTICLE);
        jdbcTemplate.update(sql, new MapSqlParameterSource().addValue(ARTICLE_ID_CAMEL, articleId));
    }
}
