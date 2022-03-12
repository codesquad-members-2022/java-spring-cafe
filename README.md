# 2022 Java Spring Cafe

2022년도 마스터즈 멤버스 백엔드 스프링 카페 프로젝트

heroku 배포 링크 : <https://java-spring-cafe.herokuapp.com>

<br/>
<details>
<summary> API 명세</summary>
<div markdown="1">
<br/>

### User

| user            | URL                 | Method | URL Params       | Data Params                                                     | Success Response                  | Error Response      |
|:----------------|---------------------|--------|------------------|-----------------------------------------------------------------|-----------------------------------|---------------------|
| listUsers       | /users              | GET    |                  |                                                                 | code: 200, view: user/list        |                     |
| showUser        | /users/:userId      | GET    | `userId: string` |                                                                 | code: 200, view: user/profile     | code: 404           |
| formCreateUser  | /users/form         | GET    |                  |                                                                 | code: 200, view: user/form        |                     |
| createUser      | /users              | POST   |                  | `userId: string, password: string, name: string, email: string` | code: 200, redirect: /users       | code: 409 code: 500 |
| formUpdateUser  | /users/:userId/form | GET    | `userId: string` |                                                                 | code: 200, view: user/update_form | code: 404           |
| updateUser      | /users/:userId      | PUT    | `userId: string` | `password: string, name: string, email: string`                 | code: 200, redirect: /users       | code: 404           |

### Article

| article            | URL           | Method | URL Params | Data Params                                       | Success Response         | Error Response      |
|--------------------|---------------|--------|------------|---------------------------------------------------|--------------------------|---------------------|
| formCreateQuestion | /questions    | GET    |            |                                                   | code: 200 view: qna/form |                     |
| createQuestion     | /questions    | POST   |            | `writer: string, title: string, contents: string` | code: 200 redirect: /    | code: 404 code: 500 |
| listQuestions      | /             | GET    |            |                                                   | code: 200 view: qna/list |                     |
| showQuestion       | /articles/:id | GET    | `id: int`  |                                                   | code: 200 view: qna/show | code: 404           |

### Auth

| auth      | URL         | Method | URL Params | Data Params                        | Success Response           | Error Response      |
|-----------|-------------|--------|------------|------------------------------------|----------------------------|---------------------|
| formLogin | /login/form | GET    |            |                                    | code: 200 view: form/login |                     |
| login     | /login      | POST   |            | `userId: string, password: string` | code: 200 redirect: /users | code: 404 code: 409 |
| logout    | /logout     | GET    |            |                                    | code: 200 redirect: /users |                     |

<br/>
</div>
</details>

<br/>
<details>
<summary> jacoco 리포트</summary>
<div markdown="1">
<br/>

### Bundle
    
![image](https://user-images.githubusercontent.com/50660684/158010241-efdcfe5b-989d-405e-9b42-bea4ee6c3362.png)

### Controller
    
![image](https://user-images.githubusercontent.com/50660684/158010292-4e4cbde2-7851-4735-9f7a-abcbfe2d26ee.png)

### Service
    
![image](https://user-images.githubusercontent.com/50660684/158010308-80312217-894f-47f1-839e-d3fd101c903b.png)

### Repository
    
![image](https://user-images.githubusercontent.com/50660684/158010331-f5e1650e-519d-46e6-b0ec-655913fc38d9.png)    
    
<br/>
</div>
</details>

<br/>
<details>
<summary>☝ 1단계 요구사항</summary>
<div markdown="1">
<br/>

## 스프링 카페 1단계 - 회원 가입 및 목록 기능

### 회원가입 기능 구현

- [X] 가입하기 페이지는 static/user/form.html을 사용한다.
- [X] static에 있는 html을 templates로 이동한다.
- [X] 사용자 관리 기능 구현을 담당할 UserController를 추가하고 애노테이션 매핑한다.
    - @Controller 애노테이션 추가
- [X] 회원가입하기 요청(POST 요청)을 처리할 메소드를 추가하고 매핑한다.
    - @PostMapping 추가하고 URL 매핑한다.
- [X] 사용자가 전달한 값을 User 클래스를 생성해 저장한다.
    - 회원가입할 때 전달한 값을 저장할 수 있는 필드를 생성한 후 setter와 getter 메소드를 생성한다.
- [X] 사용자 목록을 관리하는 ArrayList를 생성한 후 앞에서 생성한 User 인스턴스를 ArrayList에 저장한다.
- [X] 사용자 추가를 완료한 후 사용자 목록 페이지("redirect:/users")로 이동한다.

### 회원목록 기능 구현

- [X] 회원목록 페이지는 static/user/list.html을 사용한다.
- [X] static에 있는 html을 templates로 이동한다.
- [X] Controller 클래스는 회원가입하기 과정에서 추가한 UserController를 그대로 사용한다.
- [X] 회원목록 요청(GET 요청)을 처리할 메소드를 추가하고 매핑한다.
  - @GetMapping을 추가하고 URL 매핑한다.
- [X] Model을 메소드의 인자로 받은 후 Model에 사용자 목록을 users라는 이름으로 전달한다.
- [X] 사용자 목록을 user/list.html로 전달하기 위해 메소드 반환 값을 "user/list"로 한다.
- [X] user/list.html 에서 사용자 목록을 출력한다.

### 회원 프로필 정보보기

- [X] 회원 프로필 보기 페이지는 static/user/profile.html을 사용한다.
- [X] static에 있는 html을 templates로 이동한다.
- [X] 앞 단계의 사용자 목록 html인 user/list.html 파일에 닉네임을 클릭하면 프로필 페이지로 이동하도록 한다.
  - html에서 페이지 이동은 <a /> 태그를 이용해 가능하다.
  - "<a href="/users/{{userId}} />" 와 같이 구현한다.
- [X] Controller 클래스는 앞 단계에서 사용한 UserController를 그대로 사용한다.
- [X] 회원프로필 요청(GET 요청)을 처리할 메소드를 추가하고 매핑한다. 
  - @GetMapping을 추가하고 URL 매핑한다. 
  - URL은 "/users/{userId}"와 같이 매핑한다.
- [X] URL을 통해 전달한 사용자 아이디 값은 @PathVariable 애노테이션을 활용해 전달 받을 수 있다. 
- [X] ArrayList에 저장되어 있는 사용자 중 사용자 아이디와 일치하는 User 데이터를 Model에 저장한다.
- [X] user/profile.html 에서는 Controller에서 전달한 User 데이터를 활용해 사용자 정보를 출력한다.

<br/>
</div>
</details>

<br/>
<details>
<summary>✌ 2단계 요구사항</summary>
<div markdown="1">
<br/>

## 스프링 카페 2단계 - 글 쓰기 기능 구현

### 글쓰기

- [X] 게시글 페이지는 static/qna/form.html을 수정해서 사용한다.
- [X] static에 있는 html을 templates로 이동한다.
- [X] 게시글 기능 구현을 담당할 ArticleController를 추가하고 애노테이션 매핑한다.
- [X] 게시글 작성 요청(POST 요청)을 처리할 메소드를 추가하고 매핑한다.
- [X] 사용자가 전달한 값을 Article 클래스를 생성해 저장한다.
- [X] 게시글 목록을 관리하는 ArrayList를 생성한 후 앞에서 생성한 Article 인스턴스를 ArrayList에 저장한다.
- [X] 게시글 추가를 완료한 후 메인 페이지(“redirect:/”)로 이동한다.

### 글 목록 조회하기

- [X] 메인 페이지(요청 URL이 “/”)를 담당하는 Controller의 method에서 게시글 목록을 조회한다.
- [X] 조회한 게시글 목록을 Model에 저장한 후 View에 전달한다. 게시글 목록은 앞의 게시글 작성 단계에서 생성한 ArrayList를 그대로 전달한다.
- [X] View에서 Model을 통해 전달한 게시글 목록을 출력한다.

### 게시글 상세보기

- [X] 게시글 목록(qna/list.html)의 제목을 클릭했을 때 게시글 상세 페이지에 접속할 수 있도록 한다.
- [X] 게시글 상세 페이지 접근 URL은 "/articles/{index}"(예를 들어 첫번째 글은 /articles/1)와 같이 구현한다.
- [X] 게시글 객체에 id 인스턴스 변수를 추가하고 ArrayList에 게시글 객체를 추가할 때 ArrayList.size() + 1을 게시글 객체의 id로 사용한다.
- [X] Controller에 상세 페이지 접근 method를 추가하고 URL은 /articles/{index}로 매핑한다.
- [X] ArrayList에서 index - 1 해당하는 데이터를 조회한 후 Model에 저장해 /qna/show.html에 전달한다.
- [X] /qna/show.html에서는 Controller에서 전달한 데이터를 활용해 html을 생성한다.

### 회원정보 수정 화면

- [X] /user/form.html 파일을 /user/updateForm.html로 복사한 후 수정화면을 생성한다.
- [X] URL 매핑을 할 때 "/users/{id}/form"와 같이 URL을 통해 인자를 전달하는 경우 @PathVariable 애노테이션을 활용해 인자 값을 얻을 수 있다.
- [X] public String updateForm(@PathVariable String id)와 같이 구현 가능하다.
- [X] Controller에서 전달한 값을 입력 폼에서 출력하려면 value를 사용하면 된다.

### 회원정보 수정

- [X] URL 매핑을 할 때 "/users/{id}"와 같이 URL을 통해 인자를 전달하는 경우 @PathVariable 애노테이션을 활용해 인자 값을 얻을 수 있다.
- [X] UserController의 사용자가 수정한 정보를 User 클래스에 저장한다.
- [X] {id}에 해당하는 User를 DB에서 조회한다(UserRepository의 findOne()).
- [X] DB에서 조회한 User 데이터를 새로 입력받은 데이터로 업데이트한다.
- [X] UserRepository의 save() 메소드를 사용해 업데이트한다.

<br/>
</div>
</details>

<br/>
<details>
<summary>🤘 3단계 요구사항</summary>
<div markdown="1">
<br/>

## 스프링 카페 3단계 - DB에 저장하기

- [X] H2 데이터베이스 의존성을 추가하고 연동한다. 
  - ORM은 사용하지 않는다. 
  - Spring JDBC를 사용한다. 
  - DB 저장 및 조회에 필요한 SQL은 직접 작성한다.
- [X] 게시글 데이터 저장하기 
  - Article 클래스를 DB 테이블에 저장할 수 있게 구현한다. 
  - Article 테이블이 적절한 PK를 가지도록 구현한다.
- [X] 게시글 목록 구현하기 
  - 전체 게시글 목록 데이터를 DB에서 조회하도록 구현한다.
- [X] 게시글 상세보기 구현하기 
  - 게시글의 세부 내용을 DB에서 가져오도록 구현한다.
- [X] 사용자 정보 DB에 저장 
  - 회원가입을 통해 등록한 사용자 정보를 DB에 저장한다.
- [X] 배포하기
  - heroku를 사용해서 배포를 진행한다.

<br/>
</div>
</details>

<br/>
<details>
<summary>🖖 4단계 요구사항</summary>
<div markdown="1">
<br/>

## 스프링 카페 4단계 - DB에 저장하기

- [X] 로그인과 로그아웃이 기능이 정상적으로 동작한다.
  - 현재 상태가 로그인 상태이면 상단 메뉴에서 “로그아웃”, “개인정보수정”이 표시되어야 한다.
  - 현재 상테가 로그인 상태가 아니라면 상단 메뉴에서 “로그인”, “회원가입”이 표시되어야 한다.
- [X] Spring MVC에서 메소드의 인자로 HttpSession을 이용해서 로그인을 구현한다.
  - Spring Security와 같은 별도 라이브러리 등은 사용하지 않는다.
  - API가 아닌 템플릿 기반으로 구현한다.
- [X] 로그인한 사용자는 자신의 정보를 수정할 수 있어야 한다.
  - 이름, 이메일만 수정할 수 있으며, 사용자 아이디는 수정할 수 없다.
  - 비밀번호가 일치하는 경우에만 수정 가능하다.
  - 로그인한 사용자와 수정하는 계정의 id가 같은 경우만 수정하도록 한다.
  - 다른 사용자의 정보를 수정하려는 경우 에러 페이지를 만든 후 에러 메시지를 출력한다.
- [X] 권한 체크에 대한 테스트를 위해 2명 이상의 테스트 데이터를 추가한다.

<br/>
</div>
</details>

