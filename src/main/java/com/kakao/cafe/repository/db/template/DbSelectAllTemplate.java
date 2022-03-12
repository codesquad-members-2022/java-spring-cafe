package com.kakao.cafe.repository.db.template;

import com.kakao.cafe.repository.db.DbCleaner;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class DbSelectAllTemplate {
    private final DataSource dataSource;

    public DbSelectAllTemplate(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }

    public List<Object> executeQuery(String sql) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Object> list = null;

        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(sql);
            rs = pstmt.executeQuery();

            list = rowMapper(rs);
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbCleaner.close(connection, pstmt, rs, dataSource);
        }
        return list;
    }

    public abstract List<Object> rowMapper(ResultSet rs) throws SQLException;
}
