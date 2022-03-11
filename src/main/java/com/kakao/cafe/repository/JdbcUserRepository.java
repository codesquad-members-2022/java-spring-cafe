package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class JdbcUserRepository implements UserRepository {

    private final NamedParameterJdbcTemplate jdbc;
    private final RowMapper<User> userRowMapper = BeanPropertyRowMapper.newInstance(User.class);
    SimpleJdbcInsert jdbcInsert;

    public JdbcUserRepository(DataSource dataSource) {
        jdbc = new NamedParameterJdbcTemplate(dataSource);
        jdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("user");
    }

    @Override
    public User save(User user) {
        User userInformation;
        try {
            userInformation = findByUserId(user.getUserId())
                .orElseThrow(() -> new NoSuchElementException("해당되는 ID가 없습니다."));
        } catch (NoSuchElementException e) {
            SqlParameterSource parameters = new BeanPropertySqlParameterSource(user);
            jdbcInsert.execute(parameters);
            return user;
        }

        userInformation.updateUserInformation(user);

        SqlParameterSource parameters = new BeanPropertySqlParameterSource(userInformation);
        jdbc.update("UPDATE user SET name = :name, email = :email WHERE user_id = :userId", parameters);

        return user;
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        try {
            Map<String, String> parameters = Collections.singletonMap("userId", userId);
            return Optional.ofNullable(jdbc.queryForObject(
                "SELECT * FROM user WHERE user_id = :userId", parameters, userRowMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll() {
        return jdbc.query("SELECT * FROM user", userRowMapper);
    }

    @Override
    public void clear() {
        jdbc.update("DELETE * FROM user", Collections.emptyMap());
    }
}
