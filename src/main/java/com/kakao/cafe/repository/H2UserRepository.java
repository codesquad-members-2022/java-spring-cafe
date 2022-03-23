package com.kakao.cafe.repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.kakao.cafe.domain.User;

@Repository
public class H2UserRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public H2UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(User user) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("user").usingGeneratedKeyColumns("id");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("nickname", user.getNickname());
        parameters.put("email", user.getEmail());
        parameters.put("date", Date.valueOf(LocalDate.now()));
        parameters.put("password", user.getPassword());
        Number key = jdbcInsert.executeAndReturnKey(new
            MapSqlParameterSource(parameters));
        user.setId(key.intValue());
        return key.intValue();
    }

    @Override
    public void update(User user) {
        jdbcTemplate.update("update user set nickname=?, email=? where id=?",
            user.getNickname(), user.getEmail(), user.getId());
    }

    @Override
    public Optional<User> findById(int id) {
        List<User> result = jdbcTemplate.query("select * from user where id = ?",
            userRowMapper(), id);
        return result.stream().findAny();
    }

    @Override
    public Optional<User> findByNickname(String nickname) {
        List<User> result = jdbcTemplate.query("select * from user where nickname = ?",
            userRowMapper(), nickname);
        return result.stream().findAny();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        List<User> result = jdbcTemplate.query("select * from user where email = ?",
            userRowMapper(), email);
        return result.stream().findAny();
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query("select * from user", userRowMapper());
    }

    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> {
            User user = new User(rs.getString("nickname")
                , rs.getString("email")
                , rs.getString("password"));
            user.setId(rs.getInt("id"));
            user.setDate(rs.getDate("date").toLocalDate());
            return user;
        };
    }
}
