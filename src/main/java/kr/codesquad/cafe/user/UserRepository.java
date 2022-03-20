package kr.codesquad.cafe.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@SuppressWarnings({"SqlResolve", "SqlNoDataSourceInspection"})
@Repository
public class UserRepository {

    private static final String SQL_SAVE_USER = "INSERT INTO CAFE_USER VALUES (?, ?, ?, ?)";
    private static final String SQL_FIND_USER_BY = "SELECT * FROM CAFE_USER WHERE %s = ?";
    private static final String SQL_FIND_USER_BY_USERID = String.format(SQL_FIND_USER_BY, "USERID");
    private static final String SQL_FIND_USER_BY_NAME = String.format(SQL_FIND_USER_BY, "NAME");
    private static final String SQL_FIND_USER_BY_EMAIL = String.format(SQL_FIND_USER_BY, "EMAIL");
    private static final String SQL_FIND_USER_ALL = "SELECT * FROM CAFE_USER";
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void save(User user) {
        jdbcTemplate.update(SQL_SAVE_USER,
                user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
    }

    public Optional<User> findByUserId(String userId) {
        return jdbcTemplate.query(SQL_FIND_USER_BY_USERID, userRowMapper(), userId)
                .stream().findAny();
    }

    public Optional<User> findByName(String name) {
        return jdbcTemplate.query(SQL_FIND_USER_BY_NAME, userRowMapper(), name)
                .stream().findAny();
    }

    public Optional<User> findByEmail(String email) {
        return jdbcTemplate.query(SQL_FIND_USER_BY_EMAIL, userRowMapper(), email)
                .stream().findAny();
    }

    public List<User> findAll() {
        return jdbcTemplate.query(SQL_FIND_USER_ALL, userRowMapper());
    }

    private RowMapper<User> userRowMapper() {
        return ((rs, rowNum) -> {
            User user = new User();
            user.setUserId(rs.getString("userid"));
            user.setPassword(rs.getString("password"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));

            return user;
        });
    }
}
