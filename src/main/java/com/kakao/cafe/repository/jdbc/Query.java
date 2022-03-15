package com.kakao.cafe.repository.jdbc;

public enum Query {
    // article
    INSERT_ARTICLE,
    SELECT_ARTICLES,
    SELECT_ARTICLE,
    DELETE_ARTICLES,
    UPDATE_ARTICLE,
    DELETE_ARTICLE,

    // user
    COUNT_USER,
    INSERT_USER,
    UPDATE_USER,
    SELECT_USERS,
    SELECT_USER,
    DELETE_USERS,

    // reply
    INSERT_REPLY,
    SELECT_REPLY,
}
