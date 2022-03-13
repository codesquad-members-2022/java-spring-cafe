package com.kakao.cafe.repository.db.template;

import com.kakao.cafe.repository.db.DbCleaner;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
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
            return -1L;
        } finally {
            DbCleaner.close(connection, pstmt, rs, dataSource);
        }
        return -1L;
    }

    public Long executeUpdate(String sql, Object... parameters) {
        return executeUpdate(sql, getPreparedStatementSetter(parameters));
    }

    public <T> T executeQuery(String sql, RowMapper<T> mapper, PreparedStatementSetter pss) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(sql);
            pss.setParameters(pstmt);
            rs = pstmt.executeQuery();

            T value = mapper.rowMapper(rs);
            if (value != null) return value;
        } catch (SQLException e) {
            return null;
        } finally {
            DbCleaner.close(connection, pstmt, rs, dataSource);
        }
        return null;
    }

    public <T> T executeQuery(String sql, RowMapper<T> mapper, Object... parameters) {
        return executeQuery(sql, mapper, getPreparedStatementSetter(parameters));
    }

    private PreparedStatementSetter getPreparedStatementSetter(Object[] parameters) {
        return new PreparedStatementSetter() {
            @Override
            public void setParameters(PreparedStatement pstmt) throws SQLException {
                for (int i = 0; i < parameters.length; i++) {
                    pstmt.setObject(i + 1, parameters[i]);
                }
            }
        };
    }

    public <T> List<T> list(String sql, RowMapper<T> mapper, PreparedStatementSetter pss) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<T> list = new ArrayList<>();

        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(sql);
            pss.setParameters(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                list.add(mapper.rowMapper(rs));
            }

            return list;
        } catch (SQLException e) {
            return null;
        } finally {
            DbCleaner.close(connection, pstmt, rs, dataSource);
        }
    }

    public <T> List<T> list(String sql, RowMapper<T> mapper, Object... parameters) {
        return list(sql, mapper, getPreparedStatementSetter(parameters));
    }
}
