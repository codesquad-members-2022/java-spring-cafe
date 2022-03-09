package com.kakao.cafe.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class DbTest {

    @Autowired
    DataSource dataSource;

    private Connection connection;

    @BeforeEach
    void setUp() throws SQLException {
        connection = dataSource.getConnection();
    }

    @Test
    void driverCheck(){
        assertThat(dataSource).isNotNull();
        assertThat(connection).isNotNull();
    }

    @Test
    void findTest() throws SQLException {
        String SQL = "SELECT id, USER_ID, password, name, email FROM USER_INFO WHERE user_id = (?)";
        PreparedStatement pstmt = connection.prepareStatement(SQL);
        pstmt.setString(1, "testA");
        ResultSet rs = pstmt.executeQuery();

        while(rs.next()){
            System.out.println(rs.getLong("id") + " : " + rs.getString(2));
        }
    }
}
