package com.kakao.cafe.domain.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class JdbctemplateUserRepository implements UserRepository{

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public JdbctemplateUserRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User save(User user) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);

        String sql = "insert into cafe_user (userId, password, name, email) values (:userId, :password, :name, :email)";
        if(findById(user.getUserId()).isPresent()) {
            sql = "update cafe_user set name = :name, email = :email where userId = :userId";
        }

        jdbcTemplate.update(sql, parameterSource);
        return user;
    }

    @Override
    public Optional<User> findById(String id) {
        String sql = "select userId, password, name, email from cafe_user where userId = :userId;";

        try {
            User target = jdbcTemplate.queryForObject(sql, new MapSqlParameterSource("userId", id), userRowMapper());
            return Optional.ofNullable(target);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll() {
        String sql = "select userId, password, name, email from cafe_user";
        List<User> users = jdbcTemplate.query(sql, userRowMapper());
        return users;
    }

    @Override
    public void clear() {
        String sql = "delete from cafe_user";
        jdbcTemplate.update(sql, new MapSqlParameterSource());
    }

    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> new User(rs.getString("userId"),
                rs.getString("password"),
                rs.getString("name"),
                rs.getString("email"));
    }

}
