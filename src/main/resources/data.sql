-- DML

INSERT INTO `user` (user_id, password, `name`, email)
VALUES ('javajigi', 'test', '자바지기', 'javajigi@slipp.net');

INSERT INTO `user` (user_id, password, `name`, email)
VALUES ('sanjigi', 'test', '산지기', 'sanjigi@slipp.net');

INSERT INTO article (writer, title, contents)
VALUES ('sanjigi',
        'runtime 에 reflect 발동 주체 객체가 뭔지 알 방법이 있을까요?',
        'sanjigi 가 2016-01-15 18:47:00.00 에 작성한 글입니다.');

INSERT INTO article (writer, title, contents)
VALUES ('javajigi',
        '국내에서 Ruby on Rails와 Play가 활성화되기 힘든 이유는 뭘까?',
        'javajigi 가 2016-01-15 18:47:00.00 에 작성한 글입니다.');
