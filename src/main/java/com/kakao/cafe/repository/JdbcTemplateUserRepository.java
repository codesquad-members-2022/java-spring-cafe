package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class JdbcTemplateUserRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateUserRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(User user) {
        String insertSql = "INSERT INTO USERS VALUES(?, ?, ?, ?)";
        jdbcTemplate.update(insertSql, user.getUserId(), user.getPassword(), user.getName(),
            user.getEmail());
    }

    @Override
    public void update(User previousUser, User newUser) {
        String updateSql = "UPDATE USERS SET password=? , name=? , email=? WHERE id=?";
        jdbcTemplate.update(updateSql, newUser.getPassword(), newUser.getName(), newUser.getEmail(),
            previousUser.getUserId());
    }

    @Override
    public Optional<User> findById(String userId) {
        String selectSql = "SELECT * FROM USERS WHERE id=?";
        List<User> resultUsers = jdbcTemplate.query(selectSql, userRowMapper(), userId);
        return resultUsers.stream().findAny();
    }

    @Override
    public List<User> findAll() {
        String selectSql = "SELECT * FROM USERS";
        return jdbcTemplate.query(selectSql, userRowMapper());
    }

    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> new User.UserBuilder(rs.getString("id"), rs.getString("password"))
            .setEmail(rs.getString("email"))
            .setName(rs.getString("name"))
            .build();
    }
}
