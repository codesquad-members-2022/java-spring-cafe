package com.kakao.cafe.database;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class DBConnectionTest {

    private Connection connection;

    @BeforeEach
    void setUp() throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/spring-cafe-db", "sa", "");
    }

    @Test
    @DisplayName("데이터 베이스가 정상적으로 연결된 경우 검봉이라는 유저 이름을 반환해야 한다.")
    void readDbTest() throws SQLException {
        // given
        String sql = "SELECT NAME FROM TEST";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        String actualName = "검봉";
        // when
        String expectedName = null;
        if (rs.next()) {
            expectedName = rs.getString(1);
        }
        // then
        assertThat(actualName).isEqualTo(expectedName);
    }
}
