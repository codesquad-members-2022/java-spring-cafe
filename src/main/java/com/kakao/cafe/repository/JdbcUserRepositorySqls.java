package com.kakao.cafe.repository;

public class JdbcUserRepositorySqls {
    static final String UPDATE_USER = "UPDATE user SET name = :name, email = :email WHERE user_id = :userId";
    static final String SELECT_USER = "SELECT * FROM user WHERE user_id = :userId";
    static final String SELECT_ALL_USERS = "SELECT * FROM user";
    static final String DELETE_ALL_USERS = "DELETE FROM user";
}
