package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;
import java.util.Collections;
import java.util.List;
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
        User userInformation = findByUserId(user.getUserId()).orElse(null);

        if (userInformation == null) {
            jdbc.update("INSERT INTO user VALUES (:userId, :password, :name, :email)", parameters);
            return user;
        }

        jdbc.update("UPDATE user SET name = :name, email = :email WHERE user_id = :userId", parameters);
        jdbc.update("UPDATE article SET writer = :name WHERE user_id = :userId", parameters);

        return user;
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        try {
            return Optional.ofNullable(jdbc.queryForObject("SELECT * FROM user WHERE user_id = :userId", Collections.singletonMap("userId", userId), userRowMapper()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll() {
        return jdbc.query("SELECT * FROM user", userRowMapper());
    }

    @Override
    public void deleteAll() {
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
