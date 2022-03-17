# 2022 Java Spring Cafe

2022년도 마스터즈 멤버스 백엔드 스프링 카페 프로젝트

---

## Overview

- Project : Gradle Project
- Spring Boot : 2.6.2
- Language : Java
- Packaging : Jar
- Java : 11
- Dependencies : Spring Web, Thymeleaf
- IDE : IntelliJ 2020.3.2

--- 
## Tree

<details>
<summary>Click</summary>

```
.
├── main
│   ├── java
│   │   └── com
│   │       └── kakao
│   │           └── cafe
│   │               ├── AppConfig.java
│   │               ├── CafeApplication.java
│   │               ├── controller
│   │               │   ├── ArticleController.java
│   │               │   ├── ExceptionController.java
│   │               │   ├── HomeController.java
│   │               │   ├── LoginController.java
│   │               │   └── UserController.java
│   │               ├── domain
│   │               │   ├── Article.java
│   │               │   ├── User.java
│   │               │   └── dto
│   │               │       ├── ArticleForm.java
│   │               │       ├── UpdateUserForm.java
│   │               │       └── UserForm.java
│   │               ├── repository
│   │               │   ├── ArticleRepository.java
│   │               │   ├── JdbcTemplateArticleRepository.java
│   │               │   ├── JdbcTemplateUserRepository.java
│   │               │   ├── MemoryArticleRepository.java
│   │               │   ├── MemoryUserRepository.java
│   │               │   └── UserRepository.java
│   │               └── service
│   │                   ├── ArticleService.java
│   │                   ├── LoginService.java
│   │                   └── UserService.java
│   └── resources
│       ├── application.properties
│       ├── data.sql
│       ├── schema.sql
│       ├── static
│       │   ├── css
│       │   │   ├── bootstrap.min.css
│       │   │   ├── page_not_found_style.css
│       │   │   └── styles.css
│       │   ├── favicon.ico
│       │   ├── fonts
│       │   │   ├── glyphicons-halflings-regular.eot
│       │   │   ├── glyphicons-halflings-regular.svg
│       │   │   ├── glyphicons-halflings-regular.ttf
│       │   │   ├── glyphicons-halflings-regular.woff
│       │   │   └── glyphicons-halflings-regular.woff2
│       │   ├── images
│       │   │   └── 80-text.png
│       │   └── js
│       │       ├── bootstrap.min.js
│       │       ├── jquery-2.2.0.min.js
│       │       └── scripts.js
│       └── templates
│           ├── error
│           │   ├── 404.html
│           │   ├── 4xx.html
│           │   └── 500.html
│           ├── fragment
│           │   ├── head.html
│           │   ├── nav.html
│           │   ├── navbar_right.html
│           │   └── subnav.html
│           ├── index.html
│           ├── qna
│           │   ├── form.html
│           │   └── show.html
│           └── user
│               ├── form.html
│               ├── list.html
│               ├── login.html
│               ├── login_failed.html
│               ├── profile.html
│               └── updateForm.html
└── test
    └── java
        └── com
            └── kakao
                └── cafe
                    ├── CafeApplicationTests.java
                    ├── repository
                    │   ├── JdbcTemplateArticleRepositoryTest.java
                    │   ├── JdbcTemplateUserRepositoryTest.java
                    │   ├── MemoryArticleRepositoryTest.java
                    │   └── MemoryUserRepositoryTest.java
                    └── service
                        ├── ArticleServiceTest.java
                        ├── LoginServiceTest.java
                        └── UserServiceTest.java

```

</details>

---

## 스프링 카페 1단계 - 회원 가입 및 목록 기능

### 학습 목표
- 스프링 부트 프로젝트 셋업을 할 수 있다.
- 스프링 부트로 템플릿 기반 MVC 페이지를 구성하고 기능을 구현할 수 있다.
- 스프링 부트로 GET과 POST 메소드의 동작방식을 이해하고 처리할 수 있다.

### 기능 요구사항
<details>
<summary>Click</summary>

#### 웹페이지 디자인
- [x] static 폴더에 있는 기존 자료(QA 게시판)를 수정하거나 아래 디자인 기획서를 참고해서 구현한다.
    - 디자인은 자유롭게 구현해도 무방하다.
    - 별도의 데이터베이스는 사용하지 않는다.

#### 회원 가입 기능 구현
- [x] 가입하기 페이지에서 회원 가입 폼을 표시한다.
- [x] 개인정보를 입력하고 확인을 누르면 회원 목록 조회 페이지로 이동한다.

#### 회원 목록 조회 기능 구현
- [x] 목록 조회 페이지에서는 가입한 회원들의 목록을 출력한다.

#### 회원 프로필 조회 기능 구현
- [x] 회원 프로필 페이지에서는 개별 회원의 프로필 정보를 출력한다.

</details>

### 프로그래밍 요구사항

<details>
<summary>Click</summary>

#### 각 기능에 따른 url과 메소드 convention
- [x] 먼저 각 기능에 대한 대표 url을 결정한다.
  
  |HTTP Method|url|기능|
  |---|---|---|
  |GET|/|Home|
  |GET|/users|Get User List|
  |GET|/users/join|Get User Form|
  |POST|/users/join|Create an User|
  |GET|/users/{id}|Get an User|
  

#### 회원가입 기능 구현
- [x] 가입하기 페이지는 static/user/form.html을 사용한다.
- [x] static에 있는 html을 templates로 이동한다.
- [x] 사용자 관리 기능 구현을 담당할 UserController를 추가하고 애노테이션 매핑한다.
    - @Controller 애노테이션 추가
- [x] 회원가입하기 요청(POST 요청)을 처리할 메소드를 추가하고 매핑한다.
    - @PostMapping 추가하고 URL 매핑한다.
- [x] 사용자가 전달한 값을 User 클래스를 생성해 저장한다.
    - [x] 회원가입할 때 전달한 값을 저장할 수 있는 필드를 생성한 후 setter와 getter 메소드를 생성한다.
- [x] 사용자 목록을 관리하는 ArrayList를 생성한 후 앞에서 생성한 User 인스턴스를 ArrayList에 저장한다.
- [x] 사용자 추가를 완료한 후 사용자 목록 페이지("redirect:/users")로 이동한다.

#### 회원목록 기능 구현
- [x] 회원목록 페이지는 static/user/list.html을 사용한다.
- [x] static에 있는 html을 templates로 이동한다.
- [x] Controller 클래스는 회원가입하기 과정에서 추가한 UserController를 그대로 사용한다.
- [x] 회원목록 요청(GET 요청)을 처리할 메소드를 추가하고 매핑한다.
    - @GetMapping을 추가하고 URL 매핑한다.
- [x] Model을 메소드의 인자로 받은 후 Model에 사용자 목록을 users라는 이름으로 전달한다.
- [x] 사용자 목록을 user/list.html로 전달하기 위해 메소드 반환 값을 "user/list"로 한다.
- [x] user/list.html 에서 사용자 목록을 출력한다.

#### 회원 프로필 정보보기
- [x] 회원 프로필 보기 페이지는 static/user/profile.html을 사용한다.
- [x] static에 있는 html을 templates로 이동한다.
- [x] 앞 단계의 사용자 목록 html인 user/list.html 파일에 닉네임을 클릭하면 프로필 페이지로 이동하도록 한다.
    - html에서 페이지 이동은 \<a /> 태그를 이용해 가능하다.
    - [x] \<a href="/users/{{userId}}" />와 같이 구현한다.
- [x] Controller 클래스는 앞 단계에서 사용한 UserController를 그대로 사용한다.
- [x] 회원프로필 요청(GET 요청)을 처리할 메소드를 추가하고 매핑한다.
    - @GetMapping을 추가하고 URL 매핑한다.
    - URL은 "/users/{userId}"와 같이 매핑한다.
- [x] URL을 통해 전달한 사용자 아이디 값은 @PathVariable 애노테이션을 활용해 전달 받을 수 있다.
- [x] ArrayList에 저장되어 있는 사용자 중 사용자 아이디와 일치하는 User 데이터를 Model에 저장한다.
- [x] user/profile.html 에서는 Controller에서 전달한 User 데이터를 활용해 사용자 정보를 출력한다.

</details>

### View
<details>
<summary>Click</summary>

#### Home
![image](https://user-images.githubusercontent.com/67811880/157053673-fdd032e7-bd83-48dc-ab79-4b3f64a5be64.png)

#### Join
![image](https://user-images.githubusercontent.com/67811880/157053830-de6eca5e-2395-4212-88e1-d592a1376869.png)

#### User List
![image](https://user-images.githubusercontent.com/67811880/157054059-6804fc05-f8fd-4026-a55f-fa4b2449b98f.png)

#### User Profile
![image](https://user-images.githubusercontent.com/67811880/157054191-a98a71f1-8886-4392-aca0-65dd72896357.png)

#### Error Page
![image](https://user-images.githubusercontent.com/67811880/157054324-75ab9741-64f9-4055-9518-b3509b6b28f9.png)

</details>

---

## 스프링 카페 2단계 - 글 쓰기 기능 구현

### 학습 목표
- Spring Boot를 이용해서 게시글 쓰기를 구현할 수 있다.

### 기능 요구사항
<details>
<summary>Click</summary>

- [x] 사용자는 게시글을 작성할 수 있어야 한다.

- [x] 모든 사용자는 게시글 목록을 볼 수 있어야 한다.
  
- [x] 모든 사용자는 게시글 상세 내용을 볼 수 있어야 한다.
  
- [x] (선택) 사용자 정보를 수정할 수 있어야 한다.
</details>

### 게시글 View

<details>
<summary>Click</summary>

#### 게시글 작성하기
<img width="842" alt="image" src="https://user-images.githubusercontent.com/67811880/158018051-6ffa8aa0-1618-4a76-9c90-9753c0621cb2.png">

#### 게시글 모아보기
<img width="845" alt="image" src="https://user-images.githubusercontent.com/67811880/158018089-9fdcc472-2e3d-4783-8be0-a53726e89b54.png">

#### 게시글 상세보기
<img width="846" alt="image" src="https://user-images.githubusercontent.com/67811880/158018111-c6eaa9ce-ee62-4866-b586-2aeafb1f07e3.png">

</details>

### 유저 수정 View

<details>
<summary>Click</summary>

#### 유저목록 보기
<img width="844" alt="image" src="https://user-images.githubusercontent.com/67811880/158018148-9bffd980-0b97-4a9c-acfb-71b8a1ca7b2c.png">

#### 유저 수정하기
<img width="844" alt="image" src="https://user-images.githubusercontent.com/67811880/158018161-11d653e8-9850-4d74-8fdb-317c03e878d7.png">

#### 유저 수정완료
<img width="846" alt="image" src="https://user-images.githubusercontent.com/67811880/158018192-39583381-2114-423a-adfe-5d6559d64c19.png">

</details>

---

## 스프링 카페 3단계 - DB에 저장하기

### 학습목표

- Spring Boot와 H2 DB와 연동하는 방법을 학습한다.
- GitHub과 Heroku를 이용해 배포를 수행할 수 있다.

### 프로그래밍 요구사항

<details>
<summary>Click</summary>

- [x] H2 데이터베이스 연동
  - H2 데이터베이스 의존성을 추가하고 연동한다.
  - ORM은 사용하지 않는다.
  - Spring JDBC를 사용한다.
  - DB 저장 및 조회에 필요한 SQL은 직접 작성한다.
- [x] 게시글 데이터 저장하기
  - Article 클래스를 DB 테이블에 저장할 수 있게 구현한다.
  - Article 테이블이 적절한 PK를 가지도록 구현한다.
- [x] 게시글 목록 구현하기
  - 전체 게시글 목록 데이터를 DB에서 조회하도록 구현한다.
- [x] 게시글 상세보기 구현하기
  - 게시글의 세부 내용을 DB에서 가져오도록 구현한다. 
- [x] 사용자 정보 DB에 저장
  - 회원가입을 통해 등록한 사용자 정보를 DB에 저장한다.
- [x] 배포
  - Heroku로 배포를 진행하고 README에 배포 URL을 기술한다.
</details>

### Test

<details>
<summary>Click</summary>

![image](https://user-images.githubusercontent.com/67811880/158592264-8b7496c4-5e86-4aad-9067-1c5165132447.png)

</details>

--- 

## 스프링 카페 4단계 - 로그인 구현

### 학습목표

- HTTP 쿠키와 세션의 동작원리를 이해한다.
- 스프링 부트로 로그인을 구현할 수 있다.

### 기능 요구 사항

<details>
<summary>Click</summary>

- [x] 로그인과 로그아웃이 기능이 정상적으로 동작한다.
- [x] 현재 상태가 로그인 상태이면 상단 메뉴에서 “로그아웃”, “개인정보수정”이 표시되어야 한다.
- [x] 현재 상테가 로그인 상태가 아니라면 상단 메뉴에서 “로그인”, “회원가입”이 표시되어야 한다.

</details>


### 프로그래밍 요구사항

<details>
<summary>Click</summary>

- [x] Spring MVC에서 메소드의 인자로 HttpSession을 이용해서 로그인을 구현한다.
- [x] Spring Security와 같은 별도 라이브러리 등은 사용하지 않는다.
- [x] API가 아닌 템플릿 기반으로 구현한다.

</details>

### 추가 요구 사항 - (선택) 개인정보 수정 기능 추가

<details>
<summary>Click</summary>

- [x] 로그인한 사용자는 자신의 정보를 수정할 수 있어야 한다.
- [x] 이름, 이메일만 수정할 수 있으며, 사용자 아이디는 수정할 수 없다.
- [x] 비밀번호가 일치하는 경우에만 수정 가능하다.

</details>

### Test

<details>
<summary>Click</summary>

![image](https://user-images.githubusercontent.com/67811880/158849872-0289acc4-b1c2-450f-a76f-c4d7c787dcd8.png)

</details>
