CREATE TABLE `cafe_users` (
    id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    userId VARCHAR(20) NOT NULL UNIQUE,
    passwd CHAR(41) NOT NULL,
    name VARCHAR(40),
    email VARCHAR(320),
    created_date DATETIME NOT NULL,
    modified_date DATETIME NOT NULL
);
