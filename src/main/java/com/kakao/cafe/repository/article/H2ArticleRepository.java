package com.kakao.cafe.repository.article;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kakao.cafe.domain.article.Article;

@Primary
@Repository
public class H2ArticleRepository implements ArticleRepository {

    private final Logger logger = LoggerFactory.getLogger(H2ArticleRepository.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public H2ArticleRepository(
        NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Article article) {
        String sql = "INSERT INTO article (writer, title, contents, writeTime) values (:writer, :title, :contents, :writeTime)";
        jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(article));
        logger.info(sql);
        logger.info("id : {}", article.getId());
    }

    @Override
    public List<Article> findAll() {
        return null;
    }

    @Override
    public Optional<Article> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void deleteAll() {

    }
}
