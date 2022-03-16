package com.kakao.cafe.jdbcTest;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
public class SpringJdbcTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @DisplayName("DB에 저장된 행의 수는 6개이다.")
    @Test
    void read_count_test() {
        String sql = "SELECT count(*) FROM test";
        Integer result = jdbcTemplate.queryForObject(sql, Integer.class);
        assertThat(result).isEqualTo(6);
    }

    @DisplayName("DB에 저장된 행을 가져오면 List<Map<String, Object>> 형태이며, 크기는 6이다.")
    @Test
    void read_name_test() {
        String sql = "SELECT name FROM test";
        // key=name & value=lucid 형태로 저장
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        assertThat(maps.size()).isEqualTo(6);
    }

    @DisplayName("queryForObject 및 RowMapper를 사용하여 객체에 매핑할 수 있다.")
    @Test
    void read_and_mapping_to_TestUser() {
        String sql = "SELECT id, name FROM test where id = ?";
        // deprecated
        TestUser testUser1 = jdbcTemplate.queryForObject(sql, new Object[] {1}, (rs, rowNum)
            -> new TestUser(rs.getInt("id"), rs.getString("name")));

        TestUser testUser2 = jdbcTemplate.queryForObject(sql, (rs, rowNum)
            -> new TestUser(rs.getInt("id"), rs.getString("name")), 1);

        assertThat(testUser1).isEqualTo(testUser2);
    }


    static class TestUser {
        int id;
        String name;

        public TestUser(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            TestUser testUser = (TestUser)o;
            return id == testUser.id && Objects.equals(name, testUser.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name);
        }
    }

}
