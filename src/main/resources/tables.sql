DROP TABLE IF EXISTS users;
create table users(
    id varchar(100) primary key,
    pw varchar(100) not null,
    name varchar(100) not null,
    email varchar(100) not null
);
INSERT INTO users (id, pw, name, email) VALUES ('id1', 'test1', '자바지기1', 'javajigi1@slipp.net');
INSERT INTO users (id, pw, name, email) VALUES ('id2', 'test2', '자바지기2', 'javajigi2@slipp.net');

DROP TABLE IF EXISTS ARTICLE;
create table article(
    id int primary key auto_increment,
    subject varchar(1000) not null,
    content text not null,
    uploadDate datetime not null,
    writer varchar(100) not null
);
