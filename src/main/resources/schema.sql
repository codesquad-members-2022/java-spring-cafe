drop table IF EXISTS cafe_replies;
drop table IF EXISTS cafe_articles;
drop table IF EXISTS cafe_users;

create table cafe_users
(
    id bigint primary key auto_increment,
    user_id  varchar(40) not null,
    password  varchar(50) not null,
    name  varchar(40) not null,
    email  varchar(60) not null,
    restricted_enter_password  boolean default false,
    created_date  TIMESTAMP not null,
    last_updated_date  TIMESTAMP not null
);

create table cafe_articles
(
    article_id bigint primary key auto_increment,
    writer varchar(40) not null,
    title varchar(200) not null,
    content varchar(255) not null,
    writing_date DATE not null,
    cafe_users_id bigint,
    deleted boolean default false,
    constraint cafe_users_id
        foreign key (cafe_users_id )
            references cafe_users ( id )
);

create table cafe_replies
(
    reply_id bigint primary key auto_increment,
    replier varchar(40) not null,
    content varchar(255) not null,
    writing_date DATE not null,
    cafe_article_id bigint not null,
    cafe_users_user_id varchar(40) not null,
    cafe_users_id bigint not null,
    deleted boolean default false,
    constraint cafe_article_id
        foreign key (cafe_article_id )
            references cafe_articles ( article_id )
);

-- delete cafe_users;
-- alter table cafe_users alter column id restart with 1;
