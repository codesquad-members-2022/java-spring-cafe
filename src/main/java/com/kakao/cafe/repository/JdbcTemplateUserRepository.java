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
import java.util.Optional;

@Repository
public class JdbcTemplateUserRepository implements UserRepository{

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateUserRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public User save(User user) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("user").usingGeneratedKeyColumns("id");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("userid", user.getUserId());
        parameters.put("name", user.getName());
        parameters.put("password", user.getPassword());
        parameters.put("email", user.getEmail());
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        user.setId(key.intValue());
        return user;
    }

    @Override
    public Optional<User> findById(int id) {
        List<User> users = jdbcTemplate.query("select * from user where id = ?", userRowMapper(), id);
        return users.stream().findAny();
    }
    
    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> {
            User user = new User(
                    rs.getString("userid"),
                    rs.getString("name"),
                    rs.getString("password"),
                    rs.getString("email")
            );
            user.setId(rs.getInt("id"));
            return user;
        };
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        List<User> users = jdbcTemplate.query("select * from user where userid = ?", userRowMapper(), userId);
        return users.stream().findAny();
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query("select * from user", userRowMapper());
    }

    @Override
    public void update(User user, int id) {
        String sql = "update user set userid = ?, name = ?, password = ?, email = ? where id = ?";
        jdbcTemplate.update(sql,
                user.getUserId(),
                user.getName(),
                user.getPassword(),
                user.getEmail(),
                id
        );

    }

    @Override
    public void clearStore() {
        jdbcTemplate.execute("delete from user");
    }

    @Override
    public int size() {
        return findAll().size();
    }
}

