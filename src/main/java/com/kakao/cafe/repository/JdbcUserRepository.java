package com.kakao.cafe.repository;

import static com.kakao.cafe.repository.JdbcUserRepositorySqls.*;

import com.kakao.cafe.domain.User;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class JdbcUserRepository implements UserRepository {

    private final NamedParameterJdbcTemplate jdbc;
    private final SimpleJdbcInsert jdbcInsert;

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

        userInformation.update(user);

        SqlParameterSource parameters = new BeanPropertySqlParameterSource(userInformation);
        jdbc.update(UPDATE_USER, parameters);

        return user;
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        try {
            Map<String, String> parameters = Collections.singletonMap("userId", userId);
            return Optional.of(jdbc.queryForObject(SELECT_USER, parameters, userRowMapper()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll() {
        return jdbc.query(SELECT_ALL_USERS, userRowMapper());
    }

    @Override
    public void clear() {
        jdbc.update(DELETE_ALL_USERS, Collections.emptyMap());
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
