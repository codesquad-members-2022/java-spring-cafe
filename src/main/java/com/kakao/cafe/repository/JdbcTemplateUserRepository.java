package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcTemplateUserRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcTemplateUserRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public User save(User user) {
        String sql = "insert into USER (user_id, password, name, email) values (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                user.getUserId(),
                user.getPassword(),
                user.getName(),
                user.getEmail());
        return user;
    }

    @Override
    public List<User> findAll() {
        String sql = "select user_id, password, name, email from USER";
        return jdbcTemplate.query(sql, userRowMapper());
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        String sql = "select user_id, password, name, email from user where user_id = ?";
        List<User> findUser = jdbcTemplate.query(sql, userRowMapper(), userId);
        return findUser.stream().findAny();
    }

    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> new User(
                rs.getString("user_id"),
                rs.getString("password"),
                rs.getString("name"),
                rs.getString("email")
        );
    }
}
