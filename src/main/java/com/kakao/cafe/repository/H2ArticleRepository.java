package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Article;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class H2ArticleRepository implements ArticleRepository {

    private final SimpleJdbcInsert simpleJdbcInsert;
    private final JdbcTemplate jdbcTemplate;

    public H2ArticleRepository(DataSource dataSource) {
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
            .withTableName("ARTICLE")
            .usingGeneratedKeyColumns("ID");
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public int save(Article article) {
        if (article.isNewArticle()) {
            return insert(article);
        }
        return update(article);
    }

    private int insert(Article article) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("ID", article.getId());
        parameters.put("USER_ID", article.getUserId());
        parameters.put("TITLE", article.getTitle());
        parameters.put("CONTENT", article.getContent());
        parameters.put("VIEW_COUNT", article.getViewCount());
        parameters.put("CREATED_AT", article.getCreatedAt());

        return (int) simpleJdbcInsert.executeAndReturnKey(parameters);
    }

    private int update(Article article) {
        String sql = "MERGE INTO ARTICLE KEY(ID) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(con -> {
            PreparedStatement pstmt = con.prepareStatement(sql, new String[]{"ID"});
            pstmt.setInt(1, article.getId());
            pstmt.setString(2, article.getUserId());
            pstmt.setString(3, article.getTitle());
            pstmt.setString(4, article.getContent());
            pstmt.setInt(5, article.getViewCount());
            pstmt.setTimestamp(6, Timestamp.valueOf(article.getCreatedAt()));
            return pstmt;
        });

        return article.getId();
    }

    @Override
    public Optional<Article> findById(int id) {
        String sql = "SELECT * FROM ARTICLE WHERE ID = ?";
        List<Article> result = jdbcTemplate.query(sql, rowMapper(), id);
        return result.stream().findAny();
    }

    @Override
    public List<Article> findAll() {
        String sql = "SELECT * FROM ARTICLE";
        return jdbcTemplate.query(sql, rowMapper());
    }

    private RowMapper<Article> rowMapper() {
        return ((rs, rowNum) -> {
            Article article = new Article(
                rs.getInt("ID"),
                rs.getString("USER_ID"),
                rs.getString("TITLE"),
                rs.getString("CONTENT"),
                new AtomicInteger(rs.getInt("VIEW_COUNT")),
                rs.getTimestamp("CREATED_AT").toLocalDateTime());
            return new Article(rs.getInt("ID"), article);
        });
    }
}
