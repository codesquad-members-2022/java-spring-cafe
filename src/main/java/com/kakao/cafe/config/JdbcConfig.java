package com.kakao.cafe.config;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcConfig {

    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/login";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";
    private final String FAILURE = "드라이버 로딩 실패";
    private final String ERROR = "ERROR";

    public Connection getConnection() throws InterruptedException {
        Connection connection = null;

        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            //log.info(FAILURE);
        } catch (SQLException e) {
            //log.info(ERROR + e);
        }
        return connection;
    }

    public PreparedStatement getPreparedStatement(String query) throws SQLException, InterruptedException {
        return getConnection().prepareStatement(query);
    }
}
