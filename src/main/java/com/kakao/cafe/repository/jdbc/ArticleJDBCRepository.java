package com.kakao.cafe.repository.jdbc;

import com.kakao.cafe.config.QueryProps;
import com.kakao.cafe.domain.Article;
import com.kakao.cafe.repository.ArticleRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ArticleJDBCRepository implements ArticleRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final KeyHolderFactory keyHolderFactory;
    private final QueryProps queryProps;

    public ArticleJDBCRepository(NamedParameterJdbcTemplate jdbcTemplate,
        KeyHolderFactory keyHolderFactory, QueryProps queryProps) {
        this.jdbcTemplate = jdbcTemplate;
        this.keyHolderFactory = keyHolderFactory;
        this.queryProps = queryProps;
    }

    @Override
    public Article save(Article article) {
        String sql = queryProps.get("INSERT_ARTICLE");
        KeyHolder keyHolder = keyHolderFactory.newKeyHolder();

        SqlParameterSource namedParameter = new MapSqlParameterSource()
            .addValue("writer", article.getWriter())
            .addValue("title", article.getTitle())
            .addValue("contents", article.getContents())
            .addValue("createdDate", LocalDateTime.now());

        jdbcTemplate.update(sql, namedParameter, keyHolder);

        if (keyHolder.getKey() != null) {
            article.setArticleId(keyHolder.getKey().intValue());
        }
        return article;
    }

    @Override
    public List<Article> findAll() {
        return null;
    }

    @Override
    public Optional<Article> findById(Integer articleId) {
        return Optional.empty();
    }

    @Override
    public void deleteAll() {

    }
}
