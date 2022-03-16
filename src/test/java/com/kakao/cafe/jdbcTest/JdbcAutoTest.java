package com.kakao.cafe.jdbcTest;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JdbcAutoTest {

    private Logger logger = LoggerFactory.getLogger(JdbcAutoTest.class);

    @Autowired
    DataSource dataSource;

    private Connection connection;

    @BeforeEach
    void init() throws SQLException {
        connection = dataSource.getConnection();
    }

    @Test
    void driverCheck() {
        logger.info("datasource class is {}", dataSource.getClass().getName());
        logger.info("connection class is {}", connection.getClass().getName());
        Assertions.assertNotNull(dataSource);
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
