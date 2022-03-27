-- DDL

DROP TABLE IF EXISTS article CASCADE;

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
    email    VARCHAR(32)
);

DROP TABLE IF EXISTS reply;

CREATE TABLE reply
(
    id                INT         NOT NULL AUTO_INCREMENT,
    parent_article_id INT         NOT NULL,
    user_id           VARCHAR(32) NOT NULL,
    contents          CLOB,
    local_date_time   TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT reply_pk FOREIGN KEY (parent_article_id) REFERENCES article (id) ON DELETE CASCADE
);
