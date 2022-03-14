package com.kakao.cafe.repository.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.kakao.cafe.domain.user.User;

@Primary
@Repository
public class H2UserRepository implements UserRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public H2UserRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(User user) {
        String sql = "INSERT INTO `user` (userId, password, name, email) values (:userId, :password, :name, :email)";
        jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(user));
    }

    @Override
    public Optional<User> findById(String id) {
        String sql = "SELECT * FROM `user` WHERE userId = :userId";
        SqlParameterSource namedParameter = new MapSqlParameterSource("userId", id);
        User user = jdbcTemplate.queryForObject(sql, namedParameter, makeUser());
        return Optional.ofNullable(user);
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM `user`";
        return jdbcTemplate.query(sql, makeUser());
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM `user`";
        jdbcTemplate.update(sql, new MapSqlParameterSource());
    }

    private RowMapper<User> makeUser() {
        return (resultSet, rowNum) -> new User(
            resultSet.getString("userId"),
            resultSet.getString("password"),
            resultSet.getString("name"),
            resultSet.getString("email"));
    }
}
