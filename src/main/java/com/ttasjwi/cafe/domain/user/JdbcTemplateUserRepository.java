package com.ttasjwi.cafe.domain.user;

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
public class JdbcTemplateUserRepository implements UserRepository {
    
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateUserRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public String save(User user) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("user");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("user_name", user.getUserName());
        parameters.put("user_email", user.getUserEmail());
        parameters.put("password", user.getPassword());
        parameters.put("reg_date", user.getRegDate());
        jdbcInsert.execute(parameters);

        return user.getUserName();
    }

    @Override
    public Optional<User> findByUserName(String userName) {
        String sql =
                "SELECT user_name, user_email, password, reg_date\n" +
                "FROM user\n" +
                "WHERE user_name = ?";

        List<User> result = jdbcTemplate.query(sql, userRowMapper(), userName);
        return result.stream().findAny();
    }

    @Override
    public Optional<User> findByUserEmail(String userEmail) {
        String sql =
                "SELECT user_name, user_email, password, reg_date\n" +
                        "FROM user\n" +
                        "WHERE user_email = ?";
        List<User> result = jdbcTemplate.query(sql, userRowMapper(), userEmail);
        return result.stream().findAny();
    }

    @Override
    public List<User> findAll() {
        String sql =
                "SELECT user_name, user_email, password, reg_date\n" +
                "FROM user";
        return jdbcTemplate.query(sql, userRowMapper());
    }

    private RowMapper<User> userRowMapper() {
        return (rs, rowNum)
                -> User.builder()
                    .userName(rs.getString("user_name"))
                    .userEmail(rs.getString("user_email"))
                    .password(rs.getString("password"))
                    .regDate(rs.getDate("reg_date").toLocalDate())
                    .build();
    }
}
