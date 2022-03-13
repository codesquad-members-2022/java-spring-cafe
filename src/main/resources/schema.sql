DROP TABLE IF EXISTS user;

CREATE TABLE user (
    user_id VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(255),
    email VARCHAR(255),
    created_time TIMESTAMP,
    updated_time TIMESTAMP,
    PRIMARY KEY (user_id)
);

DROP TABLE IF EXISTS article;

CREATE TABLE article (
  id        BIGINT       NOT NULL AUTO_INCREMENT,
  writer    VARCHAR(100) NOT NULL,
  title     VARCHAR(100) NOT NULL,
  contents  VARCHAR(255) NOT NULL,
  created_time TIMESTAMP,
  updated_time TIMESTAMP,
  PRIMARY KEY (id)
);
