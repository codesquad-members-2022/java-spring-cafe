package com.kakao.cafe.repository.db.template;

import com.kakao.cafe.repository.db.DbCleaner;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class DbTemplate {
    private final DataSource dataSource;

    public DbTemplate(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }

    public Long executeUpdate(String sql, PreparedStatementSetter pss) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pss.setParameters(pstmt);
            pstmt.executeUpdate();

            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbCleaner.close(connection, pstmt, rs, dataSource);
            ;
        }
        return -1L;
    }

    public Object executeQuery(String sql, PreparedStatementSetter pss, RowMapper mapper) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(sql);
            pss.setParameters(pstmt);
            rs = pstmt.executeQuery();

            Object value = mapper.rowMapper(rs);
            if (value != null) return value;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbCleaner.close(connection, pstmt, rs, dataSource);
        }
        return null;
    }

    public List<Object> executeQuery(String sql, RowsMapper mapper) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Object> list = null;

        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(sql);
            rs = pstmt.executeQuery();

            list = mapper.rowsMapper(rs);
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbCleaner.close(connection, pstmt, rs, dataSource);
        }
        return list;
    }
}
