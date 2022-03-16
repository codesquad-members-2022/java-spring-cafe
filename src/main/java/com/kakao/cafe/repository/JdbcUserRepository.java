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
    private final RowMapper<User> rowMapper;

    public JdbcUserRepository(DataSource dataSource) {
        jdbc = new NamedParameterJdbcTemplate(dataSource);
        insertJdbc = new SimpleJdbcInsert(dataSource).withTableName("member").usingGeneratedKeyColumns("id");
        rowMapper = (rs, row) ->
                new User(
                        rs.getInt("id"),
                        rs.getString("user_id"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("email")
                );
    }

    @Override
    public List<User> findAll() {
        return jdbc.query("select id, user_id, password, name, email from member",
                Collections.emptyMap(), rowMapper);
    }

    @Override
    public Optional<User> save(User user) {
        findById(user.getUserId()).ifPresentOrElse(
                (other) -> merge(user),
                () -> persist(user)
        );
        return Optional.ofNullable(user);
    }

    private void persist(User user) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(user);
        user.setId(insertJdbc.executeAndReturnKey(params).intValue());
    }

    private void merge(User user) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(user);
        jdbc.update("update member set password = :password, name = :name, email = :email where user_id = :userId", params);
    }

    @Override
    public Optional<User> findById(String userId) {
        try {
            User user = jdbc.queryForObject(
                    "select id, user_id, password, name, email from member where user_id = :userId",
                    Collections.singletonMap("userId", userId), rowMapper);

            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public int deleteById(String userId) {
        return jdbc.update("delete article where userId = :userId", Collections.singletonMap("userId", userId));
    }
}
