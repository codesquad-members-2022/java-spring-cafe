package com.kakao.cafe.domain.posts;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class JdbcPostRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcPostRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void save(Post post) {
        String sql = "insert into post(writer, title, content, localdatetime ) values(?,?,?,?)";
        post.setLocalDateTime(LocalDateTime.now());

        jdbcTemplate.update(sql, post.getWriter(), post.getTitle(), post.getContents(), post.getLocalDateTime());;
    }

    public Post findById(Long id) {
        Post post = null;
        try {
            post = jdbcTemplate.query("select * from post where id = ?", postRowMapper())
                    .stream()
                    .findAny()
                    .orElseThrow(SQLException::new);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return post;
    }

    public List<Post> findAll() {
        return jdbcTemplate.query("select * from post", postRowMapper());
    }

    private RowMapper<Post> postRowMapper() {
        return (rs, rowNum) -> new Post(rs.getLong("id"), rs.getString("writer"),
                rs.getString("title"), rs.getString("content"),
                rs.getTimestamp("localdatetime").toLocalDateTime());
    }
}
