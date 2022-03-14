-- DDL

DROP TABLE IF EXISTS article;

CREATE TABLE article
(
    id              INT         NOT NULL AUTO_INCREMENT,
    user_id         VARCHAR(32) NOT NULL,
    title           VARCHAR(64),
    contents        CLOB,
    local_date_time TIMESTAMP,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS user_info;

CREATE TABLE user_info
(
    id       INT         NOT NULL AUTO_INCREMENT,
    user_id  VARCHAR(32) NOT NULL,
    password VARCHAR(32) NOT NULL,
    name     VARCHAR(32) NOT NULL,
    email    VARCHAR(32),
    PRIMARY KEY (user_id)
)
