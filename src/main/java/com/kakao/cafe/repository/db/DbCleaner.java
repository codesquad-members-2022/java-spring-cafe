package com.kakao.cafe.repository.db;

import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbCleaner {

    public static void close(Connection conn, PreparedStatement pstmt, ResultSet rs, DataSource dataSource) {
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
                close(conn, dataSource);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void close(Connection conn, DataSource dataSource) throws SQLException {
        DataSourceUtils.releaseConnection(conn, dataSource);
    }
}
