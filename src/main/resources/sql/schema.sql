DROP TABLE IF EXISTS USERS;
DROP TABLE IF EXISTS ARTICLES;

CREATE TABLE USERS
(
    id       varchar(64) NOT NULL PRIMARY KEY,
    password varchar(64),
    name     varchar(64),
    email    varchar(64)
);

CREATE TABLE ARTICLES
(
    id      int IDENTITY NOT NULL PRIMARY KEY,
    writer  varchar(64),
    title   varchar(64),
    content text
);
