package com.kakao.cafe.repository.db.template;

import com.kakao.cafe.repository.db.DbCleaner;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class DbSelectTemplate {

    private final DataSource dataSource;

    public DbSelectTemplate(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }

    public Object executeQuery(String sql) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(sql);
            setParameters(pstmt);
            rs = pstmt.executeQuery();

            Object value = rowMapper(rs);
            if (value != null) return value;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbCleaner.close(connection, pstmt, rs, dataSource);
        }
        return null;
    }

    public abstract void setParameters(PreparedStatement pstmt) throws SQLException;

    public abstract Object rowMapper(ResultSet rs) throws SQLException;
}
