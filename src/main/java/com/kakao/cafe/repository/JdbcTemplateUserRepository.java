package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public class JdbcTemplateUserRepository implements UserRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcTemplateUserRepository(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        jdbcInsert = new SimpleJdbcInsert(dataSource)
            .withTableName("USERS");
    }

    @Override
    public void save(User user) {
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(
            user);
        jdbcInsert.execute(sqlParameterSource);
    }

    @Override
    public void update(User previousUser, User newUser) {
        String updateSql = "UPDATE USERS SET password=:password, name=:name, email=:email WHERE id=:userId";
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
            .addValue("password", newUser.getPassword())
            .addValue("name", newUser.getName())
            .addValue("email", newUser.getEmail())
            .addValue("userId", previousUser.getId());
        jdbcTemplate.update(updateSql, sqlParameterSource);
    }

    @Override
    public Optional<User> findById(String userId) {
        String selectSql = "SELECT * FROM USERS WHERE id=:userId";
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
            .addValue("userId", userId);
        List<User> resultUsers = jdbcTemplate.query(selectSql, sqlParameterSource, userRowMapper());
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
