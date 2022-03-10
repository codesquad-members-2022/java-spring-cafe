CREATE TABLE article (
id int(10) AUTO_INCREMENT NOT NULL,
writer varchar(20) NOT NULL,
title varchar(20) NOT NULL,
contents TEXT,
created_date DATETIME DEFAULT CURRENT_TIMESTAMP
);
