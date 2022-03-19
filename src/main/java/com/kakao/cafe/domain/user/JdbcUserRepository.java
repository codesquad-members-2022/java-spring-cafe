package com.kakao.cafe.domain.user;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

@Repository
public class JdbcUserRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcUserRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private final static String FIND_ID_SQL = "select id, user_id, password, name, email from user where id = ?";
    private final static String FIND_USERID_SQL = "select id, user_id, password, name, email from user where user_id = ?";
    public void save(User user) {
        final String sql = "insert into user(user_id, password, name, email) values(?,?,?,?)";

        jdbcTemplate.update(sql, user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
    }

    public User findById(Long id) {
        User user = null;
        try {
            user = jdbcTemplate.query(FIND_ID_SQL, userRowMapper(), id)
                    .stream()
                    .findAny()
                    .orElseThrow(SQLException::new);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public User findByUserId(String userId) {
        User user = null;
        try {
            user = jdbcTemplate.query(FIND_USERID_SQL, userRowMapper(), userId)
                    .stream()
                    .findAny()
                    .orElseThrow(SQLException::new);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public List<User> findAll() {
        return jdbcTemplate.query("select id, user_id, password, name, email" +
                " from user", userRowMapper());
    }

    public void updateUser(Long id, User user) {
        final String sql = "update user set name = ?, email = ? where id = ?";
        jdbcTemplate.update(sql, user.getName(), user.getEmail(), id);
    }

    public User findByLoginId(String userId) {
        User user = null;
        try {
            user = jdbcTemplate.query(FIND_USERID_SQL, userRowMapper(), userId)
                    .stream().findAny().orElseThrow(SQLException::new);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> {
            User user = new User();
            user.setMapper(rs.getLong("id"), rs.getString("user_id"),
                    rs.getString("password"), rs.getString("name"),
                    rs.getString("email"));
            return user;
        };
    }
}
