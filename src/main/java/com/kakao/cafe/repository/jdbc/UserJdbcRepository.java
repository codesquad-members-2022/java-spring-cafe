package com.kakao.cafe.repository.jdbc;

import com.kakao.cafe.config.QueryProps;
import com.kakao.cafe.domain.User;
import com.kakao.cafe.exception.ErrorCode;
import com.kakao.cafe.exception.InternalOperationException;
import com.kakao.cafe.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserJdbcRepository implements UserRepository {

    private static final String USER_ID = "user_id";
    private static final String PASSWORD = "password";
    private static final String NAME = "name";
    private static final String EMAIL = "email";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final QueryProps queryProps;

    public UserJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate, QueryProps queryProps) {
        this.jdbcTemplate = jdbcTemplate;
        this.queryProps = queryProps;
    }

    @Override
    public User save(User user) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);

        String sql = count(parameterSource) == 0
            ? queryProps.get(Query.INSERT_USER)
            : queryProps.get(Query.UPDATE_USER);

        jdbcTemplate.update(sql, parameterSource);
        return user;
    }

    @Override
    public List<User> findAll() {
        String sql = queryProps.get(Query.SELECT_USERS);

        return jdbcTemplate.query(sql,
            (rs, rowNum) ->
                new User(
                    rowNum + 1,
                    rs.getString(USER_ID),
                    rs.getString(PASSWORD),
                    rs.getString(NAME),
                    rs.getString(EMAIL)
                )
        );
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        String sql = queryProps.get(Query.SELECT_USER);

        try {
            User user = jdbcTemplate.queryForObject(sql,
                new MapSqlParameterSource()
                    .addValue("userId", userId),
                (rs, rowNum) ->
                    new User(
                        rowNum + 1,
                        rs.getString(USER_ID),
                        rs.getString(PASSWORD),
                        rs.getString(NAME),
                        rs.getString(EMAIL)
                    )
            );
            return Optional.ofNullable(user);

        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }

    }

    @Override
    public void deleteAll() {
        String sql = queryProps.get(Query.DELETE_USERS);

        jdbcTemplate.update(sql, new MapSqlParameterSource());
    }

    private Integer count(BeanPropertySqlParameterSource parameterSource) {
        Integer count = jdbcTemplate.queryForObject(queryProps.get(Query.COUNT_USER),
            parameterSource, Integer.class);

        if (count == null) {
            throw new InternalOperationException(ErrorCode.INTERNAL_ERROR);
        }

        return count;
    }
}
