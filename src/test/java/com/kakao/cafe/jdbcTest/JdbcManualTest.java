package com.kakao.cafe.jdbcTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JdbcManualTest {

    private Logger logger = LoggerFactory.getLogger(JdbcManualTest.class);

    private Connection connection;
    private List<String> lists;

    @BeforeEach
    void init() throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:~/h2test;AUTO_SERVER=TRUE", "sa", "");
        lists = List.of("lucid", "vans", "nori");
    }

    @Test
    void initTest() {
        logger.info("connection name is {}", connection.getClass().getName());
        Assertions.assertNotNull(connection);
    }

    @Test
    void readDb() throws SQLException {
        Statement stmt = connection.createStatement();
        String sql = "select id, name from test";

        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            String name = rs.getString("name");
            logger.info("name =  {}", name);
        }
    }
}
