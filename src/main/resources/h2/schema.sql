DROP TABLE ARTICLE IF EXISTS CASCADE;
DROP TABLE USER IF EXISTS CASCADE;

create table USER (
    USER_ID VARCHAR(100) not null primary key,
    PASSWORD VARCHAR(100) not null,
    NAME VARCHAR(100) not null,
    EMAIL VARCHAR(100)
);

create table ARTICLE (
    ID BIGINT not null AUTO_INCREMENT primary key,
    USER_ID VARCHAR(100) not null,
    TITLE VARCHAR(100) not null,
    CONTENT VARCHAR(1000) not null,
    CREATE_DATE DATETIME not null default CURRENT_TIMESTAMP,
    foreign key (USER_ID) references USER(USER_ID)
);

