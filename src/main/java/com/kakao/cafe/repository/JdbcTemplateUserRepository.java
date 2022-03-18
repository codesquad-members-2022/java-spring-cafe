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
public class JdbcTemplateUserRepository implements UserRepository {
    private final JdbcTemplate jdbcTemplate;

        public JdbcTemplateUserRepository(DataSource dataSource) {
            this.jdbcTemplate = new JdbcTemplate(dataSource);
        }

    @Override
        public void createUser(User user) {
            SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
            jdbcInsert.withTableName("user").usingGeneratedKeyColumns("userIdx");

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("userName", user.getUserName());
            parameters.put("userPassword", user.getUserPassword());
            parameters.put("userEmail", user.getUserEmail());
            
            Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
            user.setUserIdx(key.longValue());
    }

    @Override
    public List<User> findAllUsers() {
        String sql = "Select userIdx, userName, userEmail from user";

        return jdbcTemplate.query(sql, userRowMapper());
    }

    @Override
    public Optional<User> findUser(String userName) {
        String sql = "Select userIdx, userName, userEmail From user Where userName = ?";
        List<User> query = jdbcTemplate.query(sql, userRowMapper(), userName);

        return query.stream().findAny();
    }

    private RowMapper<User> userRowMapper() {
        return (resultSet, rowNum) -> {
            User user = new User();
            user.setUserIdx(resultSet.getLong("userIdx"));
            user.setUserName(resultSet.getString("userName"));
            user.setUserEmail(resultSet.getString("userEmail"));

            return user;
        };
    }
}
