package com.kakao.cafe.domain.user;

import com.kakao.cafe.web.dto.UserUpdateDto;
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

    public void save(User user) {
        String sql = "insert into user(user_id, password, name, email) values(?,?,?,?)";

        jdbcTemplate.update(sql, user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
    }

    public User findById(Long id) {
        User user = null;
        try {
            user = jdbcTemplate.query("select * from user where id = ?", userRowMapper(), id)
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
            user = jdbcTemplate.query("select * from user where user_id = ?", userRowMapper(), userId)
                    .stream()
                    .findAny()
                    .orElseThrow(SQLException::new);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public List<User> findAll() {
        return jdbcTemplate.query("select * from user", userRowMapper());
    }

    public void updateUser(Long id, User user) {
        String sql = "update user set name = ?, email = ? where id = ?";
        jdbcTemplate.update(sql, user.getName(), user.getEmail(), id);
    }

    public User findByLoginId(String userId) {
        String sql = "select * from user where user_id = ?";
        User user = null;
        try {
            user = jdbcTemplate.query(sql, userRowMapper(), userId)
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
