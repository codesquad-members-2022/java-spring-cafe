
create table cafe_user (
                        userId varchar(255) primary key,
                        password varchar(255),
                        name varchar(255),
                        email varchar(255)
);

create table cafe_article (
                         id int  auto_increment primary key,
                         writer varchar(255),
                         title varchar(255),
                         contents varchar(255),
                         writtenTime timestamp
);

create table cafe_reply (
                        id int auto_increment,
                        articleId int,
                        writer varchar(255),
                        contents varchar(255),
                        writtenTime timestamp,
                        primary key (id),
                        foreign key (articleId) references cafe_article (id)
)

