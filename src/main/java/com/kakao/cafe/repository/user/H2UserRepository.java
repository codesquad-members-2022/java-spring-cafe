package com.kakao.cafe.repository.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kakao.cafe.domain.user.User;

@Primary
@Repository
public class H2UserRepository implements UserRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public H2UserRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(User user) {
        String sql = "INSERT INTO `user` (userId, password, name, email) values (:userId, :password, :name, :email)";
        jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(user));
    }

    @Override
    public Optional<User> findById(String id) {
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>();
    }

    @Override
    public void deleteAll() {

    }
}
