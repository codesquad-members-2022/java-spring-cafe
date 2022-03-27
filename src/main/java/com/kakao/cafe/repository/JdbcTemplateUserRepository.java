package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcTemplateUserRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateUserRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public User findByUserId(String userId) {
        List<User> result = jdbcTemplate.query("select * from userTbl where userId = ?", userRowMapper(), userId);
        return result.stream().findAny().get();
    }

    @Override
    public User findById(Long id) {
        List<User> result = jdbcTemplate.query("select * from userTbl where id = ?", userRowMapper(), id);
        return result.stream().findAny().get();
    }

    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setUserId(rs.getString("userId"));
            user.setPassword(rs.getString("password"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            return user;
        };
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query("select * from userTbl", userRowMapper());
    }

    @Override
    public User save(User user) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("userTbl").usingGeneratedKeyColumns("id");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("userId", user.getUserId());
        parameters.put("password", user.getPassword());
        parameters.put("name", user.getName());
        parameters.put("email", user.getEmail());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        user.setId(key.longValue());
        return user;
    }

    @Override
    public void update(String userId, User updateParam) {
        User savedUser = findByUserId(userId);
        jdbcTemplate.update("update userTbl set password = ?, name = ?, email = ? where id = ?",
        updateParam.getPassword(), updateParam.getName(), updateParam.getEmail(), savedUser.getId());
    }

    @Override
    public void clearStore() {

    }
}
