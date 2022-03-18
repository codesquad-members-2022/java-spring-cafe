package com.kakao.cafe;

import static org.assertj.core.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class H2Test {

    private Connection connection;

    @BeforeEach
    void setUp() throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/springcafe", "sa", "");
    }

    @Test
    void driverCheck() {
        assertThat(connection).isNotNull();
    }

    @Test
    void readDb() throws SQLException {
        String sql = "select id, name from test where id = 1";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        resultSet.next();
        int id = resultSet.getInt(1);
        String name = resultSet.getString(2);
        System.out.printf("%d %s %n", id, name);
        assertThat(id).isEqualTo(1);
        assertThat(name).isEqualTo("Hello");
    }
}
