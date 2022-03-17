package com.kakao.cafe.repository.user;

import com.kakao.cafe.domain.user.User;
import org.h2.jdbc.JdbcSQLException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserJdbcRepository implements UserRepository {

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;

    public UserJdbcRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        this.jdbcInsert.withTableName("users");
    }

    @Override
    public User save(User user) {

        Map<String, Object> params = new HashMap<>();
        params.put("userid", user.getUserId());
        params.put("password", user.getPassword());
        params.put("email", user.getEmail());
        params.put("name", user.getName());


        Optional<User> existingData = findByUserId(user.getUserId());
        if (existingData.isPresent()) {
            jdbcTemplate.update("update users set password=? , email=?, name=? where userId=?", user.getPassword(), user.getEmail(), user.getName(), user.getUserId());
        }else{
            jdbcInsert.execute(params);
        }

        return user;
    }

    @Override
    public Optional<User> findByUserId(String id) {
        List<User> result = jdbcTemplate.query("select userId, password, email, name from users where userId = ?", userRowMapper(),id);
        return result.stream().findAny();
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query("select userId, password, email, name from users", userRowMapper());
    }

    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> {
            User user = new User(
                    rs.getString("userId"),
                    rs.getString("password"),
                    rs.getString("email"),
                    rs.getString("name"));
            return user;
        };
    }
}
