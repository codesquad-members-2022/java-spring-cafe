package com.ttasjwi.cafe.domain.user;

import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Date.valueOf;

public class JdbcUserRepository implements UserRepository {

    private final DataSource dataSource;

    public JdbcUserRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public String save(User user) {
        String sql = "INSERT INTO\n" +
                "user(user_name, user_email, password, reg_date)\n" +
                "values(?,?,?,?)";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user.getUserName());
            pstmt.setString(2, user.getUserEmail());
            pstmt.setString(3, user.getPassword());
            pstmt.setDate(4, valueOf(user.getRegDate()));
            pstmt.executeUpdate();
            return user.getUserName();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public Optional<User> findByUserName(String userName) {
        String sql =
                "SELECT user_name, user_email, password, reg_date\n" +
                        "FROM user\n" +
                        "WHERE user_name= ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userName);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                User user = buildUserFromResultSet(rs);
                return Optional.of(user);
            }
            return Optional.empty();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public Optional<User> findByUserEmail(String userEmail) {
        String sql =
                "SELECT user_name, user_email, password, reg_date\n" +
                        "FROM user\n" +
                        "WHERE user_email= ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, userEmail);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                User user = buildUserFromResultSet(rs);
                return Optional.of(user);
            }
            return Optional.empty();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public List<User> findAll() {

        String sql =
                "SELECT user_name, user_email, password, reg_date\n" +
                        "FROM user";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);

            rs = pstmt.executeQuery();

            List<User> users = new ArrayList<>();

            while (rs.next()) {
                User user = buildUserFromResultSet(rs);
                users.add(user);
            }
            return users;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }

    private User buildUserFromResultSet(ResultSet rs) throws SQLException {
        return User.builder()
                .userName(rs.getString("user_name"))
                .userEmail(rs.getString("user_email"))
                .password(rs.getString("password"))
                .regDate(rs.getDate("reg_date").toLocalDate())
                .build();
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
