# 2022 Java Spring Cafe

2022년도 마스터즈 멤버스 백엔드 스프링 카페 프로젝트

## 스프링 카페 1단계 - 회원 가입 및 목록 기능

### 1단계 기능요구사항

#### 웹페이지 디자인

* [x] static 폴더에 있는 기존 자료(QA 게시판) 를 수정하거나 주어진 디자인 기획서를 참고해서 구현한다.
* [x] 디자인은 자유롭게 구현해도 무방하다. (**디자인 기획서** 및 **참고 마크업 파일** 참고)
* [x] 별도의 데이터베이스는 사용하지 않는다.

#### 회원 가입 기능 구현

* [x] 가입하기 페이지에서 회원 가입 폼을 표시한다.
* [x] 개인정보를 입력하고 확인을 누르면 회원 목록 조회 페이지로 이동한다.

#### 회원 목록 조회 기능 구현

* [x] 목록 조회 페이지에서는 가입한 회원들의 목록을 출력한다.

#### 회원 프로필 조회 기능 구현

* [x] 회원 프로필 페이지에서는 개별 회원의 프로필 정보를 출력한다.

### 1단계 프로그래밍 요구사항

#### 각 기능에 따른 url과 메소드 convention

* [x] 먼저 각 기능에 대한 대표 url을 결정한다.

| URL                 | 기능                  |
|---------------------|-----------------------|
| GET /users/new      | sign-up 화면 출력     |
| POST /users/new     | Create user           |
| GET /users          | user 목록 출력        |
| GET /users/{userId} | 회원 개별 프로필 출력 |

* [x] 예를 들어 회원관리와 관련한 기능의 대표 URL은 "/users"와 같이 설계한다.
* [x] 기능의 세부 기능에 대해 일반적으로 구현하는 기능(목록, 상세보기, 수정, 삭제)에 대해서는 URL convention을 결정한 후 사용한다. (**todo 앱 예시 참고**)

#### 회원가입 기능 구현

* [x] 가입하기 페이지는 static/user/form.html을 사용한다.
* [x] static에 있는 html을 templates로 이동한다.
* [x] 사용자 관리 기능 구현을 담당할 UserController를 추가하고 애노테이션 매핑한다.
  * [x] @Controller 애노테이션 추가
* [x] 회원가입하기 요청(POST 요청)을 처리할 메소드를 추가하고 매핑한다.
  * [x] @PostMapping 추가하고 URL 매핑한다.
* [x] 사용자가 전달한 값을 User 클래스를 생성해 저장한다.
  * [x] 회원가입할 때 전달한 값을 저장할 수 있는 필드를 생성한 후 setter와 getter 메소드를 생성한다.
* [x] 사용자 목록을 관리하는 ArrayList를 생성한 후 앞에서 생성한 User 인스턴스를 ArrayList에 저장한다.
* [x] 사용자 추가를 완료한 후 사용자 목록 페이지("redirect:/users")로 이동한다.
  * [x] **PRG 패턴**으로 새로고침 시 재가입 방지

#### 회원목록 기능 구현

* [x] 회원목록 페이지는 static/user/list.html을 사용한다.
* [x] static에 있는 html을 templates로 이동한다.
* [x] Controller 클래스는 회원가입하기 과정에서 추가한 UserController를 그대로 사용한다.
* [x] 회원목록 요청(GET 요청)을 처리할 메소드를 추가하고 매핑한다.
  * [x] @GetMapping을 추가하고 URL 매핑한다.
* [x] Model을 메소드의 인자로 받은 후 Model에 사용자 목록을 users라는 이름으로 전달한다.
* [x] 사용자 목록을 user/list.html로 전달하기 위해 메소드 반환 값을 "user/list"로 한다.
* [x] user/list.html 에서 사용자 목록을 출력한다.

#### 회원 프로필 정보보기

* [x] 회원 프로필 보기 페이지는 static/user/profile.html을 사용한다.
* [x] static에 있는 html을 templates로 이동한다.
* [x] 앞 단계의 사용자 목록 html인 user/list.html 파일에 닉네임을 클릭하면 프로필 페이지로 이동하도록 한다.
  * [x] html에서 페이지 이동은 `<a />` 태그를 이용해 가능하다.
  * [x] `<a href="/users/{{userId}}" />`와 같이 구현한다.
* [x] Controller 클래스는 앞 단계에서 사용한 UserController를 그대로 사용한다.
* [x] 회원프로필 요청(GET 요청)을 처리할 메소드를 추가하고 매핑한다.
  * [x] @GetMapping을 추가하고 URL 매핑한다.
  * [x] URL은 "/users/{userId}"와 같이 매핑한다.
* [x] URL을 통해 전달한 사용자 아이디 값은 @PathVariable 애노테이션을 활용해 전달 받는다.
* [x] ArrayList에 저장되어 있는 사용자 중 사용자 아이디와 일치하는 User 데이터를 Model에 저장한다.
* [x] user/profile.html 에서는 Controller에서 전달한 User 데이터를 활용해 사용자 정보를 출력한다.

#### HTML의 중복 제거

* [x] index.html, /user/form.html, /qna/form.html 코드를 보면 header, navigation bar, footer 부분에 많은 중복 코드를 제거한다.

---
