package com.kakao.cafe.repository.jdbctemplate;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.ArticleRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class JdbcTemplateArticleRepository implements ArticleRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateArticleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Article save(Article article) {
        String sql = "insert into ARTICLE(USER_ID, TITLE, CONTENT, CREATE_DATE) values (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"ID"});
            ps.setString(1, article.getUser().getUserId());
            ps.setString(2, article.getTitle());
            ps.setString(3, article.getContent());
            ps.setTimestamp(4, Timestamp.valueOf(article.getCreateDate()));
            return ps;
        }, keyHolder);

        int id = keyHolder.getKey().intValue();
        return new Article(id, article);
    }

    @Override
    public Article findById(int id) {
        return jdbcTemplate.queryForObject("select * from ARTICLE as a join USER as u on a.USER_ID = u.USER_ID where ID = ?", articleRowMapper(), id);
    }

    @Override
    public List<Article> findAll() {
        return jdbcTemplate.query("select * from ARTICLE as a join USER as u on a.USER_ID = u.USER_ID;", articleRowMapper());
    }

    private RowMapper<Article> articleRowMapper() {
        return (rs, rowNum) -> {
            Integer id = rs.getInt("id");
            String title = rs.getString("title");
            String content = rs.getString("content");
            LocalDateTime createDate = new Timestamp(rs.getDate("create_date").getTime()).toLocalDateTime();

            String userId = rs.getString("user_id");
            String password = rs.getString("password");
            String name = rs.getString("name");
            String email = rs.getString("email");
            User user = new User(userId, password, name, email);

            return new Article(id, user, title, content, createDate);
        };
    }
}
