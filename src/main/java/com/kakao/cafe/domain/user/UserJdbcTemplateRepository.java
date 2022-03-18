package com.kakao.cafe.domain.user;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class UserJdbcTemplateRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public UserJdbcTemplateRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void save(User user) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", user.getUserId());
        params.addValue("password", user.getPassword());
        params.addValue("name", user.getName());
        params.addValue("email", user.getEmail());
        if (isAlreadyJoin(user)) {
            namedParameterJdbcTemplate.update("UPDATE USER SET password = :password, name = :name, email = :email WHERE user_id = :userId", params);
            return;
        }
        namedParameterJdbcTemplate.update("INSERT INTO USER (user_id, password, name, email) VALUES (:userId, :password, :name, :email)", params);
    }

    private boolean isAlreadyJoin(User user) {
        return findByUserId(user.getUserId()).isPresent();
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query("SELECT * FROM USER", userRowMapper());
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        List<User> userList = jdbcTemplate.query("SELECT * FROM USER where user_id=?", userRowMapper(), userId);
        return userList.stream().findAny();
    }

    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> {
            long id = rs.getLong("id");
            String userId = rs.getString("user_id");
            String password = rs.getString("password");
            String name = rs.getString("name");
            String email = rs.getString("email");
            return new User(id, userId, password, name, email);
        };
    }
}
