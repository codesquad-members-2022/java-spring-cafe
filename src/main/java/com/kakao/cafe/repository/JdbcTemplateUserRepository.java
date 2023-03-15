package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;
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
public class JdbcTemplateUserRepository implements UserRepository{

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateUserRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);//datasource 데이터 연결을 하기 위해 주입
    }

    @Override
    public User save(User user) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("user").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("user_email", user.getEmail());
        parameters.put("user_nickname", user.getNickname());
        parameters.put("user_password", user.getPassword());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));//실행시키고 키값을 리턴한다
        user.setId(key.longValue());
        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findByNickname(String nickname) { //* 말고 컬럼명을 다 명시해주는 것이 좋다
        List<User> result = jdbcTemplate.query("select * from user where user_nickname = ?", userRowMapper(), nickname);
        return result.stream().findAny();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query("select * from user", userRowMapper());
    }

    //뭐하는 친구
    private RowMapper<User> userRowMapper(){
        return (resultSet, rowNum) -> {
            User user = new User();
            user.setId(resultSet.getLong("id"));
            user.setNickname(resultSet.getString("user_nickname"));
            user.setPassword(resultSet.getString("user_password"));
            user.setEmail(resultSet.getString("user_email"));
            return user;
        };
    }
}
