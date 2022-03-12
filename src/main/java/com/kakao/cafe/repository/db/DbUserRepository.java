package com.kakao.cafe.repository.db;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.UserRepository;
import com.kakao.cafe.repository.db.template.*;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Primary
@Repository
public class DbUserRepository implements UserRepository {
    private final DataSource dataSource;

    public DbUserRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Long save(User user) {
        PreparedStatementSetter pss = new PreparedStatementSetter() {
            @Override
            public void setParameters(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, user.getUserId());
                pstmt.setString(2, user.getPassword());
                pstmt.setString(3, user.getName());
                pstmt.setString(4, user.getEmail());
            }
        };

        DbTemplate template = new DbTemplate(dataSource);

        String SQL = "INSERT INTO user_info (user_id, password, name, email) VALUES (?, ?, ?, ?)";
        Long saveId = template.executeUpdate(SQL, pss);
        user.setId(saveId);
        return saveId;
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        PreparedStatementSetter pss = new PreparedStatementSetter() {
            @Override
            public void setParameters(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, userId);
            }
        };

        RowMapper mapper = new RowMapper() {
            @Override
            public Object rowMapper(ResultSet rs) throws SQLException {
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
        return Optional.ofNullable((User) template.executeQuery(SQL, pss, mapper));
    }

    @Override
    public List<User> findAll() {
        RowsMapper mapper = new RowsMapper() {
            @Override
            public List<Object> rowsMapper(ResultSet rs) throws SQLException {
                List<Object> list = new ArrayList<>();
                while (rs.next()) {
                    User user = new User(rs.getString("user_id"), rs.getString("password"), rs.getString("name"), rs.getString("email"));
                    user.setId(rs.getLong("id"));
                    list.add(user);
                }
                return list;
            }
        };

        DbTemplate template = new DbTemplate(dataSource);

        String SQL = "SELECT id, user_id, password, name, email FROM user_info";
        return template.executeQuery(SQL, mapper).stream()
                .map(o -> (User) o)
                .collect(Collectors.toList());
    }

    @Override
    public boolean delete(String userId) {
        PreparedStatementSetter pss = new PreparedStatementSetter() {
            @Override
            public void setParameters(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, userId);
            }
        };

        DbTemplate template = new DbTemplate(dataSource);

        String SQL = "DELETE FROM user_info WHERE user_id = (?)";
        Long resultId = template.executeUpdate(SQL, pss);

        if (resultId != -1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean update(String userId, User updateParam) {
        PreparedStatementSetter pss = new PreparedStatementSetter() {
            @Override
            public void setParameters(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, updateParam.getUserId());
                pstmt.setString(2, updateParam.getPassword());
                pstmt.setString(3, updateParam.getName());
                pstmt.setString(4, updateParam.getEmail());
                pstmt.setString(5, updateParam.getUserId());
            }
        };

        DbTemplate template = new DbTemplate(dataSource);

        String SQL = "UPDATE user_info SET user_id = (?), password = (?), name = (?), email = (?) WHERE user_id = (?)";
        Long resultId = template.executeUpdate(SQL, pss);

        if (resultId != -1) {
            return true;
        }
        return false;
    }
}
