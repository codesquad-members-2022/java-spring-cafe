-- DDL

DROP TABLE IF EXISTS article CASCADE;

CREATE TABLE article
(
    article_id   INT NOT NULL AUTO_INCREMENT,
    writer       VARCHAR(255),
    title        VARCHAR(255),
    contents     VARCHAR(255),
    created_date TIMESTAMP,
    PRIMARY KEY (article_id)
);

DROP TABLE IF EXISTS `user` CASCADE;

CREATE TABLE `user`
(
    user_id  VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    `name`   VARCHAR(255),
    email    VARCHAR(255),
    PRIMARY KEY (user_id)
);

DROP TABLE IF EXISTS reply CASCADE;

CREATE TABLE reply
(
    reply_id     INT          NOT NULL AUTO_INCREMENT,
    article_id   INT          NOT NULL,
    user_id      VARCHAR(255) NOT NULL,
    comment      VARCHAR(255),
    created_date TIMESTAMP,
    PRIMARY KEY (reply_id),
    FOREIGN KEY (article_id) REFERENCES article (article_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES `user` (user_id) ON DELETE CASCADE
);
