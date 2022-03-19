package com.kakao.cafe.core.repository.article;

import com.kakao.cafe.core.domain.article.Article;
import com.kakao.cafe.core.repository.ArticleRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.kakao.cafe.utils.TimeUtils.dateTimeOf;
import static com.kakao.cafe.utils.TimeUtils.timestampOf;
import static java.util.Optional.ofNullable;

@Repository
public class JdbcArticleRepository implements ArticleRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcArticleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Article save(Article article) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String query = "INSERT INTO articles(id, title, content, writer, create_at, last_modified_at, view_count) VALUES (null , ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(query, new String[]{"id"});
            ps.setString(1, article.getTitle());
            ps.setString(2, article.getContent());
            ps.setString(3, article.getWriter());
            ps.setTimestamp(4, timestampOf(article.getCreateAt()));
            ps.setTimestamp(5, timestampOf(article.getLastModifiedAt()));
            ps.setInt(6, article.getViewCount());
            return ps;
        }, keyHolder);
        Number key = keyHolder.getKey();
        int id = key != null ? key.intValue() : -1;
        return new Article.Builder()
                .id(id)
                .title(article.getTitle())
                .writer(article.getWriter())
                .createAt(article.getCreateAt())
                .viewCount(article.getViewCount())
                .build();
    }

    @Override
    public Optional<Article> findById(Integer id) {
        String query = "SELECT * FROM articles WHERE id=?";
        List<Article> result = jdbcTemplate.query(query, mapper, id);
        return ofNullable(result.isEmpty() ? null : result.get(0));
    }

    @Override
    public void update(Article article) {
        String query = "UPDATE articles SET title=?, content=? WHERE id=?";
        jdbcTemplate.update(query, article.getTitle(), article.getContent(), article.getId());
    }

    @Override
    public List<Article> findAll() {
        String query = "SELECT * FROM articles";
        List<Article> result = jdbcTemplate.query(query, mapper);
        if (result.isEmpty()) {
            return Collections.emptyList();
        }
        return result;
    }

    private static RowMapper<Article> mapper = (rs, rowNum) -> new Article.Builder()
            .id(rs.getInt("id"))
            .title(rs.getString("title"))
            .content(rs.getString("content"))
            .writer(rs.getString("writer"))
            .createAt(dateTimeOf(rs.getTimestamp("create_at")))
            .lastModifiedAt(dateTimeOf(rs.getTimestamp("last_modified_at")))
            .viewCount(rs.getInt("view_count"))
            .build();
}