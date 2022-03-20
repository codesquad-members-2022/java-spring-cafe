package com.kakao.cafe.repository.jdbctemplate;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.UserRepository;
import org.springframework.dao.EmptyResultDataAccessException;
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
        boolean existUserId = isExistUserId(user.getUserId());
        if (existUserId) {
            jdbcTemplate.update(
                    "update USER set PASSWORD = ?, NAME = ?, EMAIL = ? where USER_ID = ?",
                    user.getPassword(), user.getName(), user.getEmail(), user.getUserId());
        } else {
            jdbcTemplate.update("insert into USER(USER_ID, PASSWORD, NAME, EMAIL) values (?, ?, ?, ?)",
                    user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
        }
        return user;
    }

    @Override
    public User findByUserId(String userId) {
        try {
            return jdbcTemplate.queryForObject("select * from USER where USER_ID = ?", userRowMapper(), userId);
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("사용자가 존재하지 않습니다.");
        }
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query("select * from USER", userRowMapper());
    }

    @Override
    public User findByUserIdAndPassword(String userId, String password) {
        try {
            return jdbcTemplate.queryForObject("select * from USER where USER_ID = ? and PASSWORD = ?", userRowMapper(), userId, password);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
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
            String userId = rs.getString("USER_ID");
            String password = rs.getString("PASSWORD");
            String name = rs.getString("NAME");
            String email = rs.getString("EMAIL");
            return new User(userId, password, name, email);
        };
    }
}
