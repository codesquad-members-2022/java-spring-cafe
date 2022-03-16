package com.kakao.cafe.repository.jdbctemplate;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.ArticleRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class JdbcTemplateArticleRepository implements ArticleRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateArticleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Article save(Article article) {
        String sql = "insert into ARTICLE(USER_ID, CONTENTS, TITLE, CREATE_DATE) values (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"ID"});
            ps.setString(1, article.getUser().getUserId());
            ps.setString(2, article.getTitle());
            ps.setArray(3, con.createArrayOf("VARCHAR", article.getContents().toArray()));
            ps.setTimestamp(4, Timestamp.valueOf(article.getCreateDate()));
            return ps;
        }, keyHolder);

        article.setId(keyHolder.getKey().intValue());
        return article;
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
            Array rsArray = rs.getArray("contents");
            Object array = rsArray.getArray();
            Object[] objects = (Object[]) array;
            String[] strings = Arrays.copyOf(objects, objects.length, String[].class);
            List<String> contents = Arrays.stream(strings).collect(Collectors.toList());
            LocalDateTime createDate = new Timestamp(rs.getDate("create_date").getTime()).toLocalDateTime();

            String userId = rs.getString("user_id");
            String password = rs.getString("password");
            String name = rs.getString("name");
            String email = rs.getString("email");
            User user = new User(userId, password, name, email);

            return new Article(id, user, title, contents, createDate);
        };
    }
}
