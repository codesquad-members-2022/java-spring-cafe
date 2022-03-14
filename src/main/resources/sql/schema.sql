drop table if exists user;
drop table if exists article;

create table article
(
    id           int NOT NULL PRIMARY KEY,
    writer       varchar(50),
    title        varchar(50),
    contents     text,
    datetime     varchar(16)
);

