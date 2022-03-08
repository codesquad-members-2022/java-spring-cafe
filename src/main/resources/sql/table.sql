CREATE TABLE IF NOT EXISTS article (
    article_id INT NOT NULL AUTO_INCREMENT,
    writer VARCHAR(255),
    title VARCHAR(255),
    contents VARCHAR(255),
    created_date TIMESTAMP,
    PRIMARY KEY (article_id)
);