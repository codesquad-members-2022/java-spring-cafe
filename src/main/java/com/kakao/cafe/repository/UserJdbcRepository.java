package com.kakao.cafe.repository;

import com.kakao.cafe.entity.User;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

@Repository
public class UserJdbcRepository implements UserRepository {

    private final NamedParameterJdbcOperations jdbc;

    public UserJdbcRepository(DataSource dataSource) {
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public User userSave(User user) {
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(user);
        String sql = "insert into `user` (userId, name, password, email) values (:userId, :name, :password, :email)";
        jdbc.update(sql, params);
        return user;
    }

    @Override
    public Optional<User> findUserId(String userId) {
        try {
            String sql = "select id, userId, name, password, email from `user` where userId = :userId";
            Map<String, Object> params = Collections.singletonMap("userId", userId);
            User target = jdbc.queryForObject(sql, params, userRowMapper());
            return Optional.ofNullable(target);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findEmail(String userEmail) {
        try {
            String sql = "select id, userId, name, password, email from `user` where email = :email";
            Map<String, Object> params = Collections.singletonMap("email", userEmail);
            User target = jdbc.queryForObject(sql, params, userRowMapper());
            return Optional.ofNullable(target);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAllUser() {
        String sql = "select id, userId, name, password, email from `user`";
        List<User> targets = jdbc.query(sql, userRowMapper());
        return Collections.unmodifiableList(targets);
    }

    @Override
    public User userUpdate(User user) {
        String sql = "update `user` set name=:name, password=:password, email=:email where userId = :userId";
        SqlParameterSource params = new BeanPropertySqlParameterSource(user);
        jdbc.update(sql, params);
        return user;
    }

    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> new User(
                rs.getString("email"),
                rs.getString("userId"),
                rs.getString("name"),
                rs.getString("password")
        );
    }
}
