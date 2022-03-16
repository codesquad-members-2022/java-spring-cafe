DROP TABLE IF EXISTS user;

CREATE TABLE user (
id int AUTO_INCREMENT PRIMARY KEY,
user_id varchar(20) NOT NULL,
password varchar(20) NOT NULL,
name varchar(20) NOT NULL,
email varchar(30) NOT NULL
);

INSERT INTO USER (user_id, password, name, email) VALUES ('suntory', '1234', '산토리', 'suntory@naver.com');
INSERT INTO USER (user_id, password, name, email) VALUES ('dotory', '1234', '도토리', 'dotory@naver.com');
INSERT INTO USER (user_id, password, name, email) VALUES ('santori', '1234', '짭토리', 'santori@naver.com');

DROP TABLE IF EXISTS article;

CREATE TABLE article (
id int AUTO_INCREMENT PRIMARY KEY,
writer varchar(20) NOT NULL,
title varchar(20) NOT NULL,
contents TEXT,
created_date DATETIME DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO ARTICLE (writer, title, contents) VALUES ('suntory', '작성 글 제목', '작성 글 내용');
INSERT INTO ARTICLE (writer, title, contents) VALUES ('dotory', '도토리가 남긴 글', '도토리 글 내용');
