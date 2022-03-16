DROP TABLE user IF EXISTS;

create table user (
    userId varchar(100),
    password varchar(100) not null,
    name varchar(100) not null,
    email varchar(100),
    primary key (userId)
);
