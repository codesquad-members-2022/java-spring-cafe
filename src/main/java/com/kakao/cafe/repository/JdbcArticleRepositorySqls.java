package com.kakao.cafe.repository;

public class JdbcArticleRepositorySqls {

    static final String INSERT_ARTICLE = "INSERT INTO article(writer, title, contents, created_date) VALUES(:writer, :title, :contents, :createdDate)";
    static final String SELECT_ARTICLE = "SELECT * FROM article WHERE id = :id";
    static final String SELECT_ALL_ARTICLES = "SELECT * FROM article";
    static final String DELETE_ALL_ARTICLES = "DELETE FROM article";
    static final String INITIAL_COLUMN_NUMBER = "ALTER TABLE article ALTER COLUMN id RESTART WITH 1";
}
