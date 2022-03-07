package com.kakao.cafe;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class DataSourceTest {

    @Autowired
    DataSource dataSource;

    @Test
    void driverTest() {
        assertThat(dataSource).isNotNull();
    }
}
