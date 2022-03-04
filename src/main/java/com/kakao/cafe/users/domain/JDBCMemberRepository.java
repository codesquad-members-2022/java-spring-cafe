package com.kakao.cafe.users.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class JDBCMemberRepository implements MemberRepository {

    private static final String INSERT_CAFE_USERS_SQL = "INSERT INTO cafe_users (userid, passwd, name, email, created_date, modified_date) values (?,?,?,?,NOW(),NOW())";
    private static final String SELECT_ALL_CAFE_USERS_SQL = "SELECT id, userid, passwd, name, email, created_date, modified_date FROM cafe_users";
    private static final String SELECT_BY_ID_CAFE_USERS_SQL = "SELECT id, userid, passwd, name, email, created_date, modified_date FROM cafe_users WHERE id=?";
    private static final String SELECT_BY_USERID_CAFE_USERS_SQL = "SELECT id, userid, passwd, name, email, created_date, modified_date FROM cafe_users WHERE userid=?";
    private static final String DELETE_ALL_CAFE_USERS_SQL = "DELETE FROM cafe_users";

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final DataSource ds;

    @Autowired
    public JDBCMemberRepository(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public Optional<Long> save(Member user) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = ds.getConnection();
            stmt = conn.prepareStatement(INSERT_CAFE_USERS_SQL, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, user.getUserId());
            stmt.setString(2, user.getPasswd());
            stmt.setString(3, user.getName());
            stmt.setString(4, user.getEmail());
            stmt.execute();
            ResultSet generatedKeys = stmt.getGeneratedKeys();

            if (generatedKeys.next()) {
                return Optional.of(generatedKeys.getLong(1));
            }

            return Optional.empty();
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        } finally {
            closeAll(conn, stmt);
        }
    }

    @Override
    public Optional<Member> findById(Long id) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = ds.getConnection();
            stmt = conn.prepareStatement(SELECT_BY_ID_CAFE_USERS_SQL);
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(buildUser(rs));
            }

            return Optional.empty();
        } catch (SQLException | NullPointerException e) {
            logger.debug(e.getMessage(), e);
            return Optional.empty();
        } finally {
            closeAll(conn, stmt);
        }
    }

    @Override
    public Optional<Member> findByUserId(String userId) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = ds.getConnection();
            stmt = conn.prepareStatement(SELECT_BY_USERID_CAFE_USERS_SQL);
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(buildUser(rs));
            }

            return Optional.empty();
        } catch (SQLException | NullPointerException e) {
            logger.debug(e.getMessage(), e);
            return Optional.empty();
        } finally {
            closeAll(conn, stmt);
        }
    }

    @Override
    public Optional<List<Member>> findAll() {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = ds.getConnection();
            stmt = conn.prepareStatement(SELECT_ALL_CAFE_USERS_SQL);
            ResultSet rs = stmt.executeQuery();
            List<Member> users = new ArrayList<>();

            while (rs.next()) {
                users.add(buildUser(rs));
            }

            return Optional.of(Collections.unmodifiableList(users));
        } catch (SQLException | NullPointerException e) {
            logger.debug(e.getMessage(), e);
            return Optional.empty();
        } finally {
            closeAll(conn, stmt);
        }
    }

    @Override
    public void deleteAll() {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = ds.getConnection();
            stmt = conn.prepareStatement(DELETE_ALL_CAFE_USERS_SQL);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(conn, stmt);
        }
    }

    private Member buildUser(ResultSet rs) throws SQLException, NullPointerException {
        return new Member.Builder()
                .setId(rs.getLong("id"))
                .setUserId(rs.getString("userid"))
                .setPasswd(rs.getString("passwd"))
                .setName(rs.getString("name"))
                .setEmail(rs.getString("email"))
                .setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime())
                .setModifiedDate(rs.getTimestamp("modified_date").toLocalDateTime())
                .build();
    }

    private void closeAll(Connection conn, PreparedStatement stmt) {
        try {
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
