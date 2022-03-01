package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcTemplateUserRepository implements UserRepository {
    private final JdbcTemplate jdbcTemplate;

        @Autowired
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

        // 쿼리 실행, DB 에서 생성된 id 값을 받아올 때 사용하는 executeAndReturnKey() 사용
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        user.setUserIdx(key.longValue());
    }

    @Override
    public Optional<User> findUser(String userName) {
        String sql = "Select * from user Where userName = ?";

        User user = jdbcTemplate.queryForObject(sql, userRowMapper(), userName);

        return Optional.ofNullable(user);
    }

    @Override
    public List<User> findAllUsers() {
        String sql = "Select * from user";

        return jdbcTemplate.query(sql, userRowMapper());
    }

    // SQL 실행 후 ResultSet 을 전달받고, 필요한 정보를 추출해서 리턴. 람다식으로 변환할 수 있다.
    private RowMapper<User> userRowMapper() {
        return new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User();
                user.setUserIdx(rs.getLong("userIdx"));
                user.setUserName(rs.getString("userName"));
                user.setUserPassword(rs.getString("userPassword"));
                user.setUserEmail(rs.getString("userEmail"));

                return user;
            }
        };
    }
}
