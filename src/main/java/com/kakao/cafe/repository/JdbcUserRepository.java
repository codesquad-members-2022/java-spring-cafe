package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Primary
@Repository
public class JdbcUserRepository implements DomainRepository<User, String> {

    private final SimpleJdbcInsert insertJdbc;
    private final NamedParameterJdbcTemplate jdbc;

    public JdbcUserRepository(DataSource dataSource) {
        jdbc = new NamedParameterJdbcTemplate(dataSource);
        insertJdbc = new SimpleJdbcInsert(dataSource).withTableName("member").usingGeneratedKeyColumns("id");
    }

    @Override
    public List<User> findAll() {
        return jdbc.query("select id, user_id, password, name, email from member",
                Collections.emptyMap(), rowMapper());
    }

    @Override
    public Optional<User> save(User user) {
        Optional<User> other = findOne(user.getUserId());
        if (other.isPresent()) {
            return merge(user);
        }
        return persist(user);
    }

    private Optional<User> persist(User user) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(user);
        user.setId(insertJdbc.executeAndReturnKey(params).intValue());
        return Optional.ofNullable(user);
    }

    private Optional<User> merge(User user) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(user);
        jdbc.update("update member set password = :password, name = :name, email = :email where user_id = :userId", params);
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findOne(String userId) {
        try {
            Map<String, ?> params = Collections.singletonMap("userId", userId);
            User user = jdbc.queryForObject(
                    "select id, user_id, password, name, email from member where user_id = :userId", params, rowMapper());

            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private RowMapper<User> rowMapper() {
        return (rs, row) ->
                new User(
                        rs.getInt("id"),
                        rs.getString("user_id"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("email")
                );
    }
}
