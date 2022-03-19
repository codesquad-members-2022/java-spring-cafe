DROP TABLE IF EXISTS USER;
CREATE TABLE USER
(
    user_id  VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    `name`   VARCHAR(255),
    email    VARCHAR(255),
    PRIMARY KEY (user_id)
);

DROP TABLE IF EXISTS ARTICLE;
CREATE TABLE ARTICLE
(
    article_id   INT NOT NULL AUTO_INCREMENT,
    writer       VARCHAR(255),
    title        VARCHAR(255),
    contents     VARCHAR(255),
    created_time TIMESTAMP,
    PRIMARY KEY (article_id)
);
