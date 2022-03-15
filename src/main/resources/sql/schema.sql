drop table if exists `user`;
drop table if exists `article`;

create table `user`
(
    `id`       BIGINT primary key auto_increment,
    `userId`   varchar(30) NOT NULL,
    `name`     varchar(30) NOT NULL,
    `password` varchar(30) NOT NULL,
    `email`    varchar(30) NOT NULL
);

create table `article`
(
    `id`          BIGINT primary key auto_increment,
    `title`       varchar(64)   NOT NULL,
    `writer`      varchar(30)   NOT NULL,
    `contents`    text          NOT NULL,
    `dateOfIssue` smalldatetime NOT NULL
);

insert into `user` (`userId`, `name`, `password`, `email`)
values ('geombong1', '검봉1', 'pwd1', 'email1@123.com');
insert into `user` (`userId`, `name`, `password`, `email`)
values ('geombong2', '검봉2', 'pwd2', 'email2@123.com');
insert into `user` (`userId`, `name`, `password`, `email`)
values ('geombong3', '검봉3', 'pwd3', 'email3@123.com');
insert into `user` (`userId`, `name`, `password`, `email`)
values ('geombong4', '검봉4', 'pwd4', 'email4@123.com');
insert into `user` (`userId`, `name`, `password`, `email`)
values ('geombong5', '검봉5', 'pwd5', 'email5@123.com');

insert into `article` (`title`, `writer`, `contents`, `dateOfIssue`)
values ('JDBC사용법1', 'geombong1', '너무 어렵다1', now());
insert into `article` (`title`, `writer`, `contents`, `dateOfIssue`)
values ('JDBC사용법2', 'geombong2', '너무 어렵다2', now() + 1);
insert into `article` (`title`, `writer`, `contents`, `dateOfIssue`)
values ('JDBC사용법3', 'geombong3', '너무 어렵다3', now() + 2);
insert into `article` (`title`, `writer`, `contents`, `dateOfIssue`)
values ('JDBC사용법4', 'geombong4', '너무 어렵다4', now() + 3);
insert into `article` (`title`, `writer`, `contents`, `dateOfIssue`)
values ('JDBC사용법5', 'geombong5', '너무 어렵다5', now() + 4);
