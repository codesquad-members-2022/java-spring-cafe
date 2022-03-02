# 2022 Java Spring Cafe

2022년도 마스터즈 멤버스 백엔드 스프링 카페 프로젝트


#### 실행방법

- application.properties 내에 DB 정보를 입력해 주세요.
  - mysql 5.7 버전
    - spring.datasource.driver-class-name=com.mysql.jdbc.Driver
  - mysql 8 버전
    - spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver


#### 실행환경

- mysql 5.7 버전
- mysql-connector-java:5.1.49
- jdbc 연동



#### URL convention

| URL            | 기능        |
|----------------|-----------|
| GET /users     | 회원 목록 조회  |
| POST /users    | 회원 가입     |
| GET /users/:id | 회원 프로필 조회 |


