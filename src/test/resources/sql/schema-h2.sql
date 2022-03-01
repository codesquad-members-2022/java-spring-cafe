CREATE TABLE `cafe_users` (
    id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    email VARCHAR(320) NOT NULL,
    userId VARCHAR(10) NOT NULL,
    passwd CHAR(41) NOT NULL,
    created_date DATETIME NOT NULL,
    modified_date DATETIME NOT NULL
);