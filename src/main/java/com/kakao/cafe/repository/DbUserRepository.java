package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
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
        String SQL = "INSERT INTO user_info (user_id, password, name, email) VALUES (?, ?, ?, ?)";
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, user.getUserId());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getName());
            pstmt.setString(4, user.getEmail());
            pstmt.executeUpdate();

            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                user.setId(rs.getLong(1));
                return user.getId();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(connection, pstmt, rs);
        }
        return -1L;
    }

    @Override
    public Optional<User> findById(String userId) {
        String SQL = "SELECT id, user_id, password, name, email FROM user_info WHERE user_id = (?)";
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(SQL);
            pstmt.setString(1, userId);
            rs = pstmt.executeQuery();

            while(rs.next()){
                User user = new User(rs.getString("user_id"), rs.getString("password"), rs.getString("name"), rs.getString("email"));
                user.setId(rs.getLong("id"));
                return Optional.ofNullable(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(connection, pstmt, rs);
        }
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        String SQL = "SELECT id, user_id, password, name, email FROM user_info";
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<User> list = new ArrayList<>();

        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(SQL);
            rs = pstmt.executeQuery();

            while(rs.next()){
                User user = new User(rs.getString("user_id"), rs.getString("password"), rs.getString("name"), rs.getString("email"));
                user.setId(rs.getLong("id"));
                list.add(user);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(connection, pstmt, rs);
        }
        return list;
    }

    @Override
    public boolean delete(String userId) {
        String SQL = "DELETE FROM user_info WHERE user_id = (?)";
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(SQL);
            pstmt.setString(1, userId);
            int affectedLine = pstmt.executeUpdate();

            if(affectedLine == 1){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(connection, pstmt, rs);
        }
        return false;
    }

    @Override
    public boolean update(String userId, User updateParam) {
        String SQL = "UPDATE user_info SET user_id = (?), password = (?), name = (?), email = (?) WHERE user_id = (?)";
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(SQL);
            pstmt.setString(1, updateParam.getUserId());
            pstmt.setString(2, updateParam.getPassword());
            pstmt.setString(3, updateParam.getName());
            pstmt.setString(4, updateParam.getEmail());
            pstmt.setString(5, updateParam.getUserId());
            int affectedLine = pstmt.executeUpdate();

            if(affectedLine == 1){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(connection, pstmt, rs);
        }
        return false;
    }

    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }

    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (conn != null) {
                close(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void close(Connection conn) throws SQLException {
        DataSourceUtils.releaseConnection(conn, dataSource);
    }
}
