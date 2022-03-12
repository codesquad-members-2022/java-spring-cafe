package com.kakao.cafe.repository.init;

import javax.annotation.PostConstruct;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class InitialRepository {
    private final JdbcTemplate jdbcTemplate;

    public InitialRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    private void dataInitialization() {
        dropRepository();
        initialRepository();
    }

    private void dropRepository() {
        jdbcTemplate.execute("DROP TABLE IF EXISTS user");
        jdbcTemplate.execute("DROP TABLE IF EXISTS article");
    }

    private void initialRepository() {
        String createUser = "create TABLE user (\n"
            + "    userId VARCHAR(255) NOT NULL PRIMARY KEY,\n"
            + "    password VARCHAR(255) NOT NULL,\n"
            + "    name VARCHAR(255) NOT NULL,\n"
            + "    email VARCHAR(255) NOT NULL\n"
            + ")";

        String createArticle = "create TABLE IF NOT EXISTS article (\n"
            + "    id BIGINT(8) NOT NULL AUTO_INCREMENT PRIMARY KEY,\n"
            + "    writer VARCHAR(255) NOT NULL,\n"
            + "    title VARCHAR(255) NOT NULL,\n"
            + "    contents VARCHAR(255) NOT NULL,\n"
            + "    writeTime TIMESTAMP\n"
            + ")";

        jdbcTemplate.execute(createUser);
        jdbcTemplate.execute(createArticle);
    }
}
