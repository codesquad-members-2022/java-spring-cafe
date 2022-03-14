package com.kakao.cafe.repository.db;

import com.kakao.cafe.config.QueryLoader;
import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.Query;
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
    private final QueryLoader queryLoader;

    public DbUserRepository(DataSource dataSource, QueryLoader queryLoader) {
        this.dataSource = dataSource;
        this.queryLoader = queryLoader;
    }

    @Override
    public Long save(User user) {
        DbTemplate template = new DbTemplate(dataSource);
        String SQL = queryLoader.get(Query.SAVE_USER);
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
        String SQL = queryLoader.get(Query.SELECT_USER);
        return Optional.ofNullable(template.executeQuery(SQL, mapper, userId));
    }

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
        String SQL = queryLoader.get(Query.SELECT_USERS);
        return template.list(SQL, mapper);
    }

    @Override
    public boolean delete(String userId) {
        DbTemplate template = new DbTemplate(dataSource);
        String SQL = queryLoader.get(Query.DELETE_USER);
        Long resultId = template.executeUpdate(SQL, userId);

        if (resultId != -1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean update(String userId, User updateParam) {
        DbTemplate template = new DbTemplate(dataSource);
        String SQL = queryLoader.get(Query.UPDATE_USER);
        Long resultId = template.executeUpdate(SQL, updateParam.getUserId(), updateParam.getPassword(), updateParam.getName(), updateParam.getEmail(), updateParam.getUserId());

        if (resultId != -1) {
            return true;
        }
        return false;
    }
}
