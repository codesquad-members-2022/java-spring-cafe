DROP TABLE IF EXISTS `cafe_users`;

CREATE TABLE `cafe_users` (
    id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    userId VARCHAR(20) NOT NULL UNIQUE,
    passwd CHAR(41) NOT NULL,
    name VARCHAR(40) NOT NULL,
    email VARCHAR(320) NOT NULL,
    created_date DATETIME NOT NULL,
    modified_date DATETIME NOT NULL
);

DROP TABLE IF EXISTS `article`;

CREATE TABLE `article` (
                           id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
                           title VARCHAR(100) NOT NULL,
                           content TEXT,
                           view_count BIGINT NOT NULL DEFAULT (0),
                           created_date DATETIME NOT NULL,
                           modified_date DATETIME NOT NULL
);