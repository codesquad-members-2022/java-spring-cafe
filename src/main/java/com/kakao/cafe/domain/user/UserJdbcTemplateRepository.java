package com.kakao.cafe.domain.user;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class UserJdbcTemplateRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserJdbcTemplateRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(User user) {
        if (isAlreadyJoin(user)) {
            jdbcTemplate.update("UPDATE USER SET user_id = ?, password = ?, name = ?, email = ? WHERE user_id = ?",
                    user.getUserId(), user.getPassword(), user.getName(), user.getEmail(), user.getUserId());
            return;
        }
        jdbcTemplate.update("INSERT INTO USER (user_id, password, name, email) VALUES (?, ?, ?, ?)",
                user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
    }

    private boolean isAlreadyJoin(User user) {
        return !Objects.isNull(user.getId());
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query("SELECT * FROM USER", userRowMapper());
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        List<User> userList = jdbcTemplate.query("SELECT * FROM USER where user_id=?", userRowMapper(), userId);
        return userList.stream().findAny();
    }

    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> {
            long id = rs.getLong("id");
            String userId = rs.getString("user_id");
            String password = rs.getString("password");
            String name = rs.getString("name");
            String email = rs.getString("email");
            User user = new User(userId, password, name, email);
            user.setId(id);
            return user;
        };
    }
}
