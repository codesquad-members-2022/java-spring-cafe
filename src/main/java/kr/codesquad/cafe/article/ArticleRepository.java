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

    private static final String SQL_SAVE_ARTICLE =
            "INSERT INTO ARTICLE(WRITER_USERID, WRITER_NAME, TITLE, CONTENTS) VALUES(?, ?, ?, ?)";
    private static final String SQL_UPDATE_ARTICLE =
            "UPDATE ARTICLE SET WRITER_NAME=?, TITLE=?, CONTENTS=? WHERE ID=?";
    private static final String SQL_FIND_ARTICLE = "SELECT * FROM ARTICLE WHERE ID = ?";
    private static final String SQL_FIND_ARTICLE_ALL = "SELECT * FROM ARTICLE";
    private static final String SQL_DELETE_ARTICLE = "DELETE FROM ARTICLE WHERE ID=?";
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ArticleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Article article) {
        if (article.hasId()) {
            jdbcTemplate.update(SQL_UPDATE_ARTICLE,
                    article.getWriterName(), article.getTitle(), article.getContents(), article.getId());
            return;
        }
        jdbcTemplate.update(SQL_SAVE_ARTICLE,
                article.getWriterUserId(), article.getWriterName(), article.getTitle(), article.getContents());
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
            article.setWriterUserId(rs.getString("writer_userid"));
            article.setWriterName(rs.getString("writer_name"));
            article.setTitle(rs.getString("title"));
            article.setContents(rs.getString("contents"));

            return article;
        });
    }

    public void deleteById(long id) {
        jdbcTemplate.update(SQL_DELETE_ARTICLE, id);
    }
}
