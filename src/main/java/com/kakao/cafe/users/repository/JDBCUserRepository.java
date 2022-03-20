package com.kakao.cafe.users.repository;

import com.kakao.cafe.users.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class JDBCUserRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public JDBCUserRepository(DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
    }

    @Override
    public Optional<User> save(User user) {
        try {
            if (!simpleJdbcInsert.isCompiled()) {
                simpleJdbcInsert.withTableName("cafe_users").usingGeneratedKeyColumns("id");
            }
            Number number = simpleJdbcInsert.executeAndReturnKey(new BeanPropertySqlParameterSource(user));

            user.setId(number.longValue());

            return Optional.of(user);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findById(Long id) {
        String findByIdSql = "SELECT id, userid, passwd, name, email, created_date, modified_date FROM cafe_users WHERE id=?";
        List<User> userList = jdbcTemplate.query(findByIdSql, userRowMapper(), id);

        if (userList.size() > 0) {
            return Optional.of(userList.get(0));
        }

        return Optional.empty();
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        String findByUserIdSql = "SELECT id, userid, passwd, name, email, created_date, modified_date FROM cafe_users WHERE userid=?";
        List<User> userList = jdbcTemplate.query(findByUserIdSql, userRowMapper(), userId);

        if (userList.size() > 0) {
            return Optional.of(userList.get(0));
        }

        return Optional.empty();
    }

    @Override
    public Optional<List<User>> findAll() {
        String findAllSql = "SELECT id, userid, passwd, name, email, created_date, modified_date FROM cafe_users";
        List<User> userList = jdbcTemplate.query(findAllSql, userRowMapper());

        if (userList.size() > 0) {
            return Optional.of(userList);
        }

        return Optional.of(Collections.emptyList());
    }

    @Override
    public void deleteAll() {
        String deleteAllSql = "DELETE FROM cafe_users";
        jdbcTemplate.update(deleteAllSql);
    }

    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> new User.Builder()
                .setId(rs.getLong("id"))
                .setUserId(rs.getString("userid"))
                .setPasswd(rs.getString("passwd"))
                .setName(rs.getString("name"))
                .setEmail(rs.getString("email"))
                .setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime())
                .setModifiedDate(rs.getTimestamp("modified_date").toLocalDateTime())
                .build();
    }
}
