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
