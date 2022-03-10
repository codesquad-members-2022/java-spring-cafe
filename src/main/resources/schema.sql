-- drop table if exists cafe_user;

create table cafe_user (
                        userId varchar(255) primary key,
                        password varchar(255),
                        name varchar(255),
                        email varchar(255)
);

