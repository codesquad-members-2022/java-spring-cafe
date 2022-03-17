-- DML

INSERT INTO user_info (user_id, password, name, email)
VALUES ('test1', '1234', 'test1', 'test1@gmail.com');

INSERT INTO user_info (user_id, password, name, email)
VALUES ('test2', '5678', 'test2', 'test2@gmail.com');

INSERT INTO article (user_id, title, contents, local_date_time)
VALUES ('test1', 'test게시물 입니다', '맥북 너무 좋아~', now());

INSERT INTO article (user_id, title, contents, local_date_time)
VALUES ('test2', 'Shine공주 입니다', '샤랄라 빛나는 중입니다!', now());
