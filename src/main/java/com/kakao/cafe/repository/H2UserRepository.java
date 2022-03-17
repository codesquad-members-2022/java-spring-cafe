package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class H2UserRepository implements UserRepository {

    private final SimpleJdbcInsert simpleJdbcInsert;
    private final JdbcTemplate jdbcTemplate;

    public H2UserRepository(DataSource dataSource) {
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
            .withTableName("MEMBER")
            .usingGeneratedKeyColumns("ID");
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public int save(User user) {
        if (user.isNewUser()) {
            return insert(user);
        }
        return update(user);
    }

    private int insert(User user) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("ID", user.getId());
        parameters.put("USER_ID", user.getUserId());
        parameters.put("EMAIL", user.getEmail());
        parameters.put("PASSWORD", user.getPassword());
        parameters.put("CREATED_AT", user.getCreatedAt());

        return (int) simpleJdbcInsert.executeAndReturnKey(parameters);
    }

    private int update(User user) {
        String sql = "MERGE INTO MEMBER KEY(ID) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(con -> {
            PreparedStatement pstmt = con.prepareStatement(sql, new String[]{"ID"});
            pstmt.setInt(1, user.getId());
            pstmt.setString(2, user.getUserId());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getPassword());
            pstmt.setTimestamp(5, Timestamp.valueOf(user.getCreatedAt()));
            return pstmt;
        });

        return user.getId();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * FROM MEMBER WHERE EMAIL = ?";
        List<User> result = jdbcTemplate.query(sql, rowMapper(), email);
        return result.stream().findAny();
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        String sql = "SELECT * FROM MEMBER WHERE USER_ID = ?";
        List<User> result = jdbcTemplate.query(sql, rowMapper(), userId);
        return result.stream().findAny();
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM MEMBER";
        return jdbcTemplate.query(sql, rowMapper());
    }

    private RowMapper<User> rowMapper() {
        return ((rs, rowNum) -> new User(
            rs.getInt("ID"),
            rs.getString("EMAIL"),
            rs.getString("USER_ID"),
            rs.getString("PASSWORD"),
            rs.getTimestamp("CREATED_AT").toLocalDateTime()));
    }
}
