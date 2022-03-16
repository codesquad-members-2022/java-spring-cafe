DROP TABLE if exists user CASCADE;
CREATE TABLE user (
    user_name VARCHAR(255),
    user_email VARCHAR(255),
    password VARCHAR(255),
    reg_date DATE,
    PRIMARY KEY (user_name),
    UNIQUE (user_name, user_email)
);