package kr.codesquad.cafe.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@SuppressWarnings({"SqlNoDataSourceInspection", "SqlResolve"})
@Repository
public class ArticleRepository {

    private static final String SQL_SAVE_ARTICLE = "INSERT INTO ARTICLE(WRITER, TITLE, CONTENTS) VALUES(?, ?, ?)";
    private static final String SQL_FIND_ARTICLE = "SELECT * FROM ARTICLE WHERE ID = ?";
    private static final String SQL_FIND_ARTICLE_ALL = "SELECT * FROM ARTICLE";
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ArticleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Article article) {
        jdbcTemplate.update(SQL_SAVE_ARTICLE,
                article.getWriter(), article.getTitle(), article.getContents());
    }

    public Optional<Article> findOne(long id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_FIND_ARTICLE, articleRowMapper(), id));
    }

    public List<Article> findAll() {
        return jdbcTemplate.query(SQL_FIND_ARTICLE_ALL, articleRowMapper());
    }

    private RowMapper<Article> articleRowMapper() {
        return ((rs, rowNum) -> {
            Article article = new Article();
            article.setId(rs.getLong("id"));
            article.setTimestamp(rs.getTimestamp("timestamp").toLocalDateTime());
            article.setWriter(rs.getString("writer"));
            article.setTitle(rs.getString("title"));
            article.setContents(rs.getString("contents"));

            return article;
        });
    }
}
