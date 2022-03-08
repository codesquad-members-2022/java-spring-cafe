package com.kakao.cafe.repository.jdbc;

import com.kakao.cafe.config.QueryProps;
import com.kakao.cafe.domain.User;
import com.kakao.cafe.exception.CustomException;
import com.kakao.cafe.exception.ErrorCode;
import com.kakao.cafe.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserJdbcRepository implements UserRepository {

    private static final String USER_ID = "user_id";
    private static final String PASSWORD = "password";
    private static final String NAME = "name";
    private static final String EMAIL = "email@example.com";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final QueryProps queryProps;

    public UserJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate, QueryProps queryProps) {
        this.jdbcTemplate = jdbcTemplate;
        this.queryProps = queryProps;
    }

    @Override
    public User save(User user) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);

        String sql = count(parameterSource) > 0
            ? queryProps.get(Query.UPDATE_USER)
            : queryProps.get(Query.INSERT_USER);

        jdbcTemplate.update(sql, parameterSource);
        return user;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        return Optional.empty();
    }

    @Override
    public void deleteAll() {

    }

    private Integer count(BeanPropertySqlParameterSource parameterSource) {
        Integer count = jdbcTemplate.queryForObject(queryProps.get(Query.COUNT_USER),
            parameterSource, Integer.class);

        if (count == null)
            throw new CustomException(ErrorCode.BAD_REQUEST);

        return count;
    }
}
