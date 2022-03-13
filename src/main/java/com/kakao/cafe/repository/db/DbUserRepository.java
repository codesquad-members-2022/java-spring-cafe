package com.kakao.cafe.repository.db;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.UserRepository;
import com.kakao.cafe.repository.db.template.DbTemplate;
import com.kakao.cafe.repository.db.template.RowMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Primary
@Repository
public class DbUserRepository implements UserRepository {
    private final DataSource dataSource;

    public DbUserRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Long save(User user) {
        DbTemplate template = new DbTemplate(dataSource);
        String SQL = "INSERT INTO user_info (user_id, password, name, email) VALUES (?, ?, ?, ?)";
        Long saveId = template.executeUpdate(SQL, user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
        user.setId(saveId);
        return saveId;
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        RowMapper<User> mapper = new RowMapper<User>() {
            @Override
            public User rowMapper(ResultSet rs) throws SQLException {
                while (rs.next()) {
                    User user = new User(rs.getString("user_id"), rs.getString("password"), rs.getString("name"), rs.getString("email"));
                    user.setId(rs.getLong("id"));
                    return user;
                }
                return null;
            }
        };

        DbTemplate template = new DbTemplate(dataSource);

        String SQL = "SELECT id, user_id, password, name, email FROM user_info WHERE user_id = (?)";
        return Optional.ofNullable(template.executeQuery(SQL, mapper, userId));
    }

//    @Override
//    public List<User> findAll() {
//        RowsMapper mapper = new RowsMapper<User>() {
//            @Override
//            public List<User> rowsMapper(ResultSet rs) throws SQLException {
//                List<User> list = new ArrayList<>();
//                while (rs.next()) {
//                    User user = new User(rs.getString("user_id"), rs.getString("password"), rs.getString("name"), rs.getString("email"));
//                    user.setId(rs.getLong("id"));
//                    list.add(user);
//                }
//                return list;
//            }
//        };
//
//        DbTemplate template = new DbTemplate(dataSource);
//
//        String SQL = "SELECT id, user_id, password, name, email FROM user_info";
//        return template.findAllQuery(SQL, mapper);
//    }

    @Override
    public List<User> findAll() {
        RowMapper<User> mapper = new RowMapper<User>() {
            @Override
            public User rowMapper(ResultSet rs) throws SQLException {
                User user = new User(rs.getString("user_id"), rs.getString("password"), rs.getString("name"), rs.getString("email"));
                user.setId(rs.getLong("id"));
                return user;
            }
        };

        DbTemplate template = new DbTemplate(dataSource);

        String SQL = "SELECT id, user_id, password, name, email FROM user_info";
        return template.list(SQL, mapper);
    }

    @Override
    public boolean delete(String userId) {
        DbTemplate template = new DbTemplate(dataSource);

        String SQL = "DELETE FROM user_info WHERE user_id = (?)";
        Long resultId = template.executeUpdate(SQL, userId);

        if (resultId != -1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean update(String userId, User updateParam) {
        DbTemplate template = new DbTemplate(dataSource);

        String SQL = "UPDATE user_info SET user_id = (?), password = (?), name = (?), email = (?) WHERE user_id = (?)";
        Long resultId = template.executeUpdate(SQL, updateParam.getUserId(), updateParam.getPassword(), updateParam.getName(), updateParam.getEmail(), updateParam.getUserId());

        if (resultId != -1) {
            return true;
        }
        return false;
    }
}
