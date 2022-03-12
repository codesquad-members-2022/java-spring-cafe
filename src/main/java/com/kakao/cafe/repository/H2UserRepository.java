package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.entity.UserEntity;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.*;
import java.util.stream.Collectors;

@Primary
@org.springframework.stereotype.Repository
public class H2UserRepository implements Repository<User, String> {

    private SimpleJdbcInsert insertJdbc;
    private NamedParameterJdbcTemplate jdbc;
    private RowMapper<UserEntity> rowMapper = BeanPropertyRowMapper.newInstance(UserEntity.class);

    public H2UserRepository(DataSource dataSource) {
        jdbc = new NamedParameterJdbcTemplate(dataSource);
        insertJdbc = new SimpleJdbcInsert(dataSource).withTableName("member").usingGeneratedKeyColumns("id");    }

    @Override
    public List<User> findAll() {
        List<UserEntity> entities = jdbc.query("select id, user_id, password, name, email from member",
                Collections.emptyMap(), rowMapper);

        return new Vector<>(entities.stream().map(entity -> entity.convertToUser()).collect(Collectors.toList()));
    }

    @Override
    public Optional<User> save(User user) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(UserEntity.of(user));
        Optional<User> other = findOne(user.getUserId());

        User result = other.isEmpty()
                ? persist(user, params)
                : merge(user, params);

        return Optional.ofNullable(result);
    }
    private User persist(User user, SqlParameterSource params) {
        user.setId(insertJdbc.executeAndReturnKey(params).longValue());
        return user;
    }
    private User merge(User user, SqlParameterSource params) {
        jdbc.update("update member set password = :password, name = :name, email = :email where user_id = :userId", params);
        return user;
    }

    @Override
    public Optional<User> findOne(String userId) {
        try {
            Map<String, ?> params = Collections.singletonMap("userId", userId);
            UserEntity entity = jdbc.queryForObject(
                    "select id, user_id, password, name, email from member where user_id = :userId", params, rowMapper);

            return Optional.ofNullable(entity.convertToUser());
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void clear() {

    }
}
