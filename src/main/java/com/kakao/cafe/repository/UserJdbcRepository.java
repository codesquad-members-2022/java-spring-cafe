package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.*;

@Repository
@Primary
public class UserJdbcRepository implements UserRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public UserJdbcRepository(DataSource dataSource) {
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public User save(User user) {
        if (findByUserId(user.getUserId()).isPresent()) {
            return update(user);
        }

        String sql = "INSERT INTO user (user_id, password, name, email, created_time, updated_time) " +
                     "VALUES (:userId, :password, :name, :email, :created_time, :updated_time)";

        namedParameterJdbcTemplate.update(sql, generateParams(user));

        return findByUserId(user.getUserId()).orElse(user);
    }

    private User update(User user) {
        String sql = "UPDATE user " +
                     "SET name = :name, email = :email " +
                     "WHERE user_id = :userId";

        namedParameterJdbcTemplate.update(sql, generateParams(user));

        return findByUserId(user.getUserId()).orElse(user);
    }

    private Map<String, Object> generateParams(User user) {
        Map<String, Object> params = new HashMap<>();

        params.put("userId", user.getUserId());
        params.put("password", user.getPassword());
        params.put("name", user.getName());
        params.put("email", user.getEmail());
        params.put("created_time", user.getCreatedTime());
        params.put("updated_time", user.getUpdatedTime());

        return params;
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        String sql = "SELECT user_id, password, name, email, created_time, updated_time " +
                     "FROM user " +
                     "WHERE user_id = :userId";

        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);

        try {
            return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(sql, params, userRowMapper()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll() {
        return namedParameterJdbcTemplate.query(
                "SELECT user_id, password, name, email, created_time, updated_time " +
                    "FROM user",
                Collections.emptyMap(),
                userRowMapper());
    }

    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) ->
                new User(
                        rs.getString("user_id"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getObject("created_time", LocalDateTime.class),
                        rs.getObject("updated_time", LocalDateTime.class)
                );
    }
}
