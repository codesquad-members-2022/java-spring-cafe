
create table users(
    id varchar(100) primary key,
    pw varchar(100) not null,
    name varchar(100) not null,
    email varchar(100) not null
);

create table article(
    id int primary key auto_increment,
    subject varchar(1000) not null,
    content text not null,
    uploadDate datetime not null,
    writer varchar(100) not null
);
