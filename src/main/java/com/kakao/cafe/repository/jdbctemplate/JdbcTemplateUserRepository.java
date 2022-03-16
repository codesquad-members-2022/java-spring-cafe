package com.kakao.cafe.repository.jdbctemplate;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.UserRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcTemplateUserRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User save(User user) {
        jdbcTemplate.update("insert into USER(USER_ID, PASSWORD, NAME, EMAIL) values (?, ?, ?, ?)",
                user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
        return user;
    }

    @Override
    public User findByUserId(String userId) {
        return jdbcTemplate.queryForObject("select * from USER where USER_ID = ?", userRowMapper(), userId);
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query("select * from USER", userRowMapper());
    }

    @Override
    public boolean isExistUserId(String userId) {
        return jdbcTemplate.queryForObject("select EXISTS (SELECT * FROM USER where USER_ID = ?)", boolean.class, userId);
    }

    @Override
    public boolean isExistEmail(String email) {
        return jdbcTemplate.queryForObject("select EXISTS (SELECT * FROM USER where EMAIL = ?)", boolean.class, email);
    }

    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> {
            String userId = rs.getString("userId");
            String password = rs.getString("password");
            String name = rs.getString("name");
            String email = rs.getString("email");
            return new User(userId, password, name, email);
        };
    }
}
