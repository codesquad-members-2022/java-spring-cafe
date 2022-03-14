package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class JdbcUserRepository implements UserRepository {

    private final NamedParameterJdbcTemplate jdbc;

    public JdbcUserRepository(DataSource dataSource) {
        jdbc = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public User save(User user) {
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(user);
        jdbc.update("INSERT INTO user VALUES (:userId, :password, :name, :email) ON DUPLICATE KEY UPDATE name = :name, email = :email", parameters);

        return user;
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        try {
            Map<String, String> parameters = Collections.singletonMap("userId", userId);
            return Optional.ofNullable(jdbc.queryForObject("SELECT * FROM user WHERE user_id = :userId", parameters, userRowMapper()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll() {
        return jdbc.query("SELECT * FROM user", userRowMapper());
    }

    @Override
    public void clear() {
        jdbc.update("DELETE FROM user", Collections.emptyMap());
    }

    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> {
            User user = new User(rs.getString("user_id"),
                                 rs.getString("password"),
                                 rs.getString("name"),
                                 rs.getString("email"));
            return user;
        };
    }
}
