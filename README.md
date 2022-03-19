# 2022 Java Spring Cafe

2022년도 마스터즈 멤버스 백엔드 스프링 카페 프로젝트

## :m: 스프링 카페 1단계 - 회원 가입 및 목록 기능

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

## :m: 스프링 카페 2단계 - 글 쓰기 기능 구현

### 2단계 기능요구사항

* [x] 사용자는 게시글을 작성할 수 있어야 한다.
* [x] 모든 사용자는 게시글 목록을 볼 수 있어야 한다.
* [x] 모든 사용자는 게시글 상세 내용을 볼 수 있어야 한다.
* [x] 사용자 정보를 수정할 수 있어야 한다.

### 2단계 프로그래밍 요구사항

#### 글쓰기

* [x] 게시글 페이지는 static/qna/form.html을 수정해서 사용한다.
* [x] static에 있는 html을 templates로 이동한다.
* [x] 게시글 기능 구현을 담당할 ArticleController를 추가하고 애노테이션 매핑한다.
* [x] 게시글 작성 요청(POST 요청)을 처리할 메소드를 추가하고 매핑한다.
* [x] 사용자가 전달한 값을 Article 클래스를 생성해 저장한다.
* [ ] 게시글 목록을 관리하는 ArrayList를 생성한 후 앞에서 생성한 Article 인스턴스를 ArrayList에 저장한다.
  * **Map으로 구현했습니다. -> 추후 ArrayList로 변경 예정**
* [x] 게시글 추가를 완료한 후 메인 페이지(“redirect:/”)로 이동한다.

#### 글 목록 조회하기

* [x] 메인 페이지(요청 URL이 “/”)를 담당하는 Controller의 method에서 게시글 목록을 조회한다.
* [ ] 조회한 게시글 목록을 Model에 저장한 후 View에 전달한다. 게시글 목록은 앞의 게시글 작성 단계에서 생성한 ArrayList를 그대로 전달한다.
  * **Map으로 구현했습니다. -> 추후 ArrayList로 변경 예정**
* [x] View에서 Model을 통해 전달한 게시글 목록을 출력한다.
  * [x] 게시글 목록을 구현하는 과정은 사용자 목록을 구현하는 html 코드를 참고한다.

#### 게시글 상세보기

* [x] 게시글 목록(qna/list.html)의 제목을 클릭했을 때 게시글 상세 페이지에 접속할 수 있도록 한다.
  * [x] 게시글 상세 페이지 접근 URL은 "/articles/{index}"(예를 들어 첫번째 글은 /articles/1)와 같이 구현한다.
  * [ ] 게시글 객체에 id 인스턴스 변수를 추가하고 ArrayList에 게시글 객체를 추가할 때 ArrayList.size() + 1을 게시글 객체의 id로 사용한다.
    * **Map으로 구현했습니다. -> 추후 ArrayList로 변경 예정**
* [x] Controller에 상세 페이지 접근 method를 추가하고 URL은 /articles/{index}로 매핑한다.
* [ ] ArrayList에서 index - 1 해당하는 데이터를 조회한 후 Model에 저장해 /qna/show.html에 전달한다.
* [x] /qna/show.html에서는 Controller에서 전달한 데이터를 활용해 html을 생성한다.

### 2단계 추가 요구사항

> 회원 목록에서 회원가입한 사용자의 정보를 수정할 수 있어야 한다.  
> 비밀번호, 이름, 이메일만 수정할 수 있으며, 사용자 아이디는 수정할 수 없다.  
> 비밀번호가 일치하는 경우에만 수정 가능하다.  

#### 회원 정보 수정 화면

* [x] 회원가입한 사용자 정보를 수정할 수 있는 수정 화면과 사용자가 수정한 값을 업데이트할 수 있는 기능을 나누어 개발해야 한다.
* [x] 사용자 정보를 수정하는 화면 구현 과정은 다음과 같다.
* [x] /user/form.html 파일을 /user/updateForm.html로 복사한 후 수정화면을 생성한다.
* [x] URL 매핑을 할 때 "/users/{id}/form"와 같이 URL을 통해 인자를 전달하는 경우 @PathVariable 애노테이션을 활용해 인자 값을 얻을 수 있다.
* [x] public String updateForm(@PathVariable String id)와 같이 구현 가능하다.
* [x] Controller에서 전달한 값을 입력 폼에서 출력하려면 value를 사용하면 된다.
* `<input type="text" name="name" value="{{name}}" />`

#### 회원 정보 수정

* [x] URL 매핑을 할 때 "/users/{id}"와 같이 URL을 통해 인자를 전달하는 경우 @PathVariable 애노테이션을 활용해 인자 값을 얻을 수 있다.
* [x] UserController의 사용자가 수정한 정보를 User 클래스에 저장한다.
* [x] {id}에 해당하는 User를 DB에서 조회한다(UserRepository의 findOne()).
* [x] DB에서 조회한 User 데이터를 새로 입력받은 데이터로 업데이트한다.
* [x] UserRepository의 save() 메소드를 사용해 업데이트한다.
* [x] **PUT method** 사용

* [x] 수정 화면(updateForm.html)에서 수정된 값을 전송할 때 PUT값을 다음과 같이 hidden으로 전송한다.
  * [x] `<input type="hidden" name="_method" value="PUT" />`
* [x] 위와 같이 _method로 PUT을 전달하면 UserController에서는 @PutMapping으로 URL을 매핑할 수 있다.

---

## :m: 스프링 카페 3단계 - DB에 저장하기

### 3단계 프로그래밍 요구사항

#### H2 데이터베이스 연동

* [x] H2 데이터베이스 의존성을 추가하고 연동한다.
* [x] ORM은 사용하지 않는다.
* [x] Spring JDBC를 사용한다.
* [x] DB 저장 및 조회에 필요한 SQL은 직접 작성한다.

#### 게시글 데이터 저장하기

* [x] Article 클래스를 DB 테이블에 저장할 수 있게 구현한다.
* [x] Article 테이블이 적절한 PK를 가지도록 구현한다.

#### 게시글 목록 구현하기

* [x] 전체 게시글 목록 데이터를 DB에서 조회하도록 구현한다.

#### 게시글 상세보기 구현하기

* [x] 게시글의 세부 내용을 DB에서 가져오도록 구현한다.

#### 사용자 정보 DB에 저장

* [x] 회원가입을 통해 등록한 사용자 정보를 DB에 저장한다.

#### 배포

* [x] Heroku로 배포를 진행하고 README에 배포 URL을 기술한다.

[배포 URL link](https://dukcode-java-spring-cafe.herokuapp.com)

---

## :m: 스프링 카페 4단계 - 로그인 구현

### 4단계 기능 요구사항

* [x] 로그인과 로그아웃이 기능이 정상적으로 동작한다.
* [x] 현재 상태가 로그인 상태이면 상단 메뉴에서 “로그아웃”, “개인정보수정”이 표시되어야 한다.
* [x] 현재 상태가 로그인 상태가 아니라면 상단 메뉴에서 “로그인”, “회원가입”이 표시되어야 한다.

### 4단계 프로그래밍 요구사항

* [x] Spring MVC에서 메소드의 인자로 HttpSession을 이용해서 로그인을 구현한다.
* [x] Spring Security와 같은 별도 라이브러리 등은 사용하지 않는다.
* [x] API가 아닌 템플릿 기반으로 구현한다.

### 4단계 추가 요구사항

#### 개인정보 수정 기능 추가

* [x] 로그인한 사용자는 자신의 정보를 수정할 수 있어야 한다.
* [ ] 이름, 이메일만 수정할 수 있으며, 사용자 아이디는 수정할 수 없다.
* [ ] 비밀번호가 일치하는 경우에만 수정 가능하다.
