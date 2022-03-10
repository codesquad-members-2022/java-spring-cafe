package com.kakao.cafe.domain.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@Primary
public class JdbctemplateUserRepository implements UserRepository{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbctemplateUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(User user) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("cafe_user");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("userId", user.getUserId());
        parameters.put("password", user.getPassword());
        parameters.put("name", user.getName());
        parameters.put("email", user.getEmail());

        jdbcInsert.execute(parameters);
    }

    @Override
    public Optional<User> findById(String id) {
        String sql = "select userId, password, name, email from cafe_user where userId = ?";
        List<User> users = jdbcTemplate.query(sql, userRowMapper(), id);
        return users.stream().findAny();
    }

    @Override
    public List<User> findAll() {
        String sql = "select userId, password, name, email from cafe_user";
        List<User> users = jdbcTemplate.query(sql, userRowMapper());
        return users;
    }

    @Override
    public void clear() {
        String sql = "delete from cafe_user";
        jdbcTemplate.execute(sql);
    }

    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> new User(rs.getString("userId"),
                rs.getString("password"),
                rs.getString("name"),
                rs.getString("email"));
    }
}
