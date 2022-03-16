
# History
프로젝트 변경 내역을 기록하는 마크다운 문서

---

## Step1

- [x] 회원 가입 기능 구현
- [x] 회원 목록 기능 구현
- [x] 회원 프로필 기능 구현

---

### 1. 회원 도메인

회원 도메인을 정의했다.

1. 도메인 프로퍼티
   - userName : 사용자 닉네임 (String). 중복 허용 x. PK로 사용할 생각.
   - userEmail : 사용자 이메일 (String). 중복 허용 x.
   - password : 사용자 패스워드 (String)
   - regDate : 사용자 가입일 (LocalDate)

2. 생성자
   - 기본생성자
   - userName, userEmail, password

3. 메서드
   - getter, setter : 모든 프로퍼티에 대한 getter, setter
   - equals, hashcode : userName 기준 동등성 정의 오버라이드
   - toString : 디버그용으로 toString 오버라이드


### 2. 사용자 저장 계층 UserRepository 정의, 테스트코드 작성

사용자 저장계층 UserRepository를 정의하고 테스트코드를 작성했다.

1. 설계
   - 일단 사양 변경은 고려하지 않고 바로 구현체로 생성
   - 동시성을 고려하여 ConcurrentHashMap으로 저장. userName(String)이 key, User가 value
   

2. 메서드
   - `save(User user)` : 사용자 저장
     - 검증 : 하위 메서드들로 사용자 이름, 이메일 중복성을 검증하여 중복되면 예외를 throw
   - `findByUserName(username)` : 사용자 이름으로 조회 후 User를 Opitonal로 반환
   - `findByUserName(username)` : 사용자 이름으로 조회 후 User를 Opitonal로 반환
   - `findAll()` : 사용자 전원을 새로운 List를 생성하여 반환. (컬렉션 원본을 넘기지 않음)

3. 테스트 코드
   - 저장 후 동일한 이름으로 조회시 같은 유저가 반환되는지 테스트
   - 저장 후 동일한 이메일로 조회시 같은 유저가 반환되는지 테스트
   - 중복되는 이름의 회원 등록 시 예외 발생
   - 중복되는 메일의 회원 등록 시 예외 발생
   - findAll 메서드 호출 시 회원을 제대로 리스트로 반환하는지 테스트

### 3. home.html 및 의존 정적 리소스 추가

1. 홈페이지에 해당하는 `home.html`을 추가했다.
2. `home.html`이 의존하는 정적 리소스들을 추가했다.
  - `reset.css`, `home.css`
  - `KakaoCI.png`

### 4. 홈 컨트롤러 추가

HomeController 및 테스트 코드를 추가했다.

- home() : GET 요청으로 "/"이 맵핑되면, view로 "home" 반환

### 5. createUserForm.html 및 의존 정적 리소스 추가

회원가입 폼 createUserForm.html을 추가함.

- post방식으로 회원가입을 수행하기 위함
- 필요한 css파일인 createUserForm.css도 추가했다.

### 6. UserController 및 createForm 메서드 추가

회원 관련 로직을 담당하는 UserController 및 회원가입 폼을 반환하는 createForm 메서드(+ 테스트코드) 추가

- UserController : 회원 관련 로직을 담당
- createForm : `/users/new`로 GET 요청 -> `/users/createUserForm`이 반환됨
- createForm의 테스트코드 또한 추가했다.

### 7. 회원목록 userList.html 및 의존 정적 리소스 추가

회원 목록 페이지를 담당하는 userList.html 및 의존 정적 리소스를 추가했다.

- 내부적으로 th:each 문법을 적용하여 반복적으로 회원 목록을 출력하기 위함
- userList.css 추가

### 8. UserController - list 메서드 및 테스트 코드 추가(회원목록 구현 완료)

회원 목록 페이지로 맵핑되는 list 메서드 추가,

- `list(...)` : GET 메서드로 `/users`가 맵핑되면, 회원 전체 list를 Model에 담고 `/users/userList` 반환
- 테스트코드도 추가했다.

### 9. UserController - create 메서드 및 테스트 코드 추가(회원가입 구현 완료)

회원 가입 기능 create 메서드 추가 및 컨트롤러 테스트 코드 추가

- `create(...)` : Post 메서드로 `/users/new`가 맵핑되면, 회원을 가입시키고 `"redirect:/users` 반환
- 테스트코드도 추가했다.

## 10. createUserForm.css -> userForm.css 파일명 변경

- 프로필 페이지에서 사용하기 위해 공유할 목적으로, css파일명을 변경했다.

## 11. userProfile.html 추가

- 사용자 프로필을 표시하는 userProfile.html을 추가했다.
- 사용자 프로필을 동적으로 표시하기 위한 html파일이다.

## 12. UserController - profile 메서드 추가

사용자 프로필 요청을 처리하는 profile 메서드를 추가했다.

- GET 요청을 통해 "/users/{userName}"가 맵핑되면, userName을 통해 사용자를 조회하여 모델에 담고 "/users/userProfile"을 반환
- 테스트 코드는 현재 역량으로는 매번 독립적인 mvc 테스트를 할 수 없어서 작성하지 못 했다.

## 13. userList.html - 프로필로 이동 기능 구현 완료

- 사용자 이름 클릭 시 "/users/{userName}"으로 GET요청을 하도록 html 문서 수정

## 14. UserService 분리, UserService 테스트코드 작성

- UserController에서 파라미터 맵핑/뷰 반환, 비즈니스 로직(+검증)을 모두 하는 상황에서 역할 분리가 필요하다고 느꼈다.
- 비즈니스 로직을 수행하는 UserService를 분리했고 컨트롤러는 UserService를 의존하도록 했다. UserService는 UserRepository를 의존한다.

---

# Step2

- [x] 게시글 작성
- [x] 게시글 목록
- [x] 게시글 상세 내용
- [ ] (선택) 사용자 정보 수정

---

## 2.1 createArticleForm.html 및 의존 정적 리소스 추가

게시글 작성 페이지를 담당하는 createArticleForm.html 및 의존 정적 리소스를 추가했다.

- 의존 정적 리소스 : createArticleForm.css 추가

## 2.2 ArticleController - createForm 메서드 추가

게시글 작성 페이지로 맵핑되어 글쓰기 폼의 viewName을 반환하는 createFrom 메서드를 추가했다.

- createForm : `/articles/new`로 GET 요청 -> `/articles/createArticleForm`이 반환됨

---

## 2.3 게시글을 정의한 Article 정의

게시글 도메인을 정의했다.

1. 도메인 프로퍼티
   - title : 게시글 제목
   - content : 게시글 내용
   - writer : 작성자
   - regDate : 게시글 등록일 (LocalDate)

2. 생성자
   - 기본생성자
   - title, writer

3. 메서드
   - getter, setter : 모든 프로퍼티에 대한 getter, setter
   - toString : 디버그용으로 toString 오버라이드
   
4. 고민했던 부분
   - 복수의 게시글 군에서 게시글을 식별할 무언가가 필요한데 현재로서는 요구사항에 ArrayList로 구현하라는 말이 있어서, 게시글 접근은 저장 인덱스로 하기로 하고 id와 같은 기본키는 별도로 지정하지 않았다.

## 2.4 ArticleController - create 메서드 추가(기능 미완성)

- 게시글 등록 폼에서 POST 요청이 들어왔을 때 맵핑되는 메서드를 추가했다.
- 게시글의 파라미터들을 읽고, Article로 맵핑하여 생성해준다.
- 작성자는 현재 로그인 기능이 별도로 존재하지 않으므로 임의로 지정한 사용자명을 세팅한다.
- 날짜는 작성시점을 기준으로 LocalDate.now()를 사용한다.
- viewName으로 "redirect:/"이 반환된다. 즉 게시글을 작성되면 홈으로 리다이렉트 된다.
- 게시글을 실제로 저장계층에 등록하는 로직은 수행하지 않았다.

---

## 2.5 게시글 저장계층 ArticleRepository 생성

게시글 저장 계층 ArticleRepository 정의, 테스트코드 작성

1. 설계
   - 일단 사양 변경은 고려하지 않고 바로 구현체로 생성
   - 동시성을 고려하여 `Collections.synchronizedList`로 저장.
   - 요구사항이 ArrayList로 구현이므로 인덱스를 기준으로 게시글을 식별한다.
   - 조회할 때는 인덱스+1을 게시글 id로 하여 식별한다.

2. 메서드
   - `save(Article article)` : 게시글 저장
   - `findByArticleId(Article articleId)` : articleId로 게시글을 조회한다. 실제 리스트에서 조회시는 id-1을 인덱스로 하여 조회
     - 만약 1 이하 또는 size를 벗어나는 범위의 게시글을 조회시 `NoSuchElementException`을 throw한다.
   - `findAll()` : 게시글 전체를 새로 리스트를 생성하여 반환함.

3. 테스트 코드
   - 위에서 정의한 예외상황을 고려하여 테스트를 작성했다.

## 2.6 ArticleController - ArticleRepository 연동

- create 메서드로 ArticleRepository에 실제로 게시글을 저장하도록 함.
- 저장후 홈으로 리다이렉트 된다.

## 2.7 HomeController - ArticleRepository 연동

- HomeController가 ArticleRepository를 알고 있음
- GET 방식에 의해 home 메서드 호출 시 Model에 Article 목록을 모델에 담음.

## 2.8 home.html - 게시글 목록 렌더링 추가

- home.html에서, model에 담긴 articles를 동적으로 렌더링하도록 함

## 2.9 "/articles" 요청에 대한 맵핑 추가

- "/articles" 요청시 "/"로 리다이렉트 되도록 함

## 2.10 "/articles/{articleId}" 요청시 게시글 보이도록 함

- "/articles/{articleId}" 접근에 대한 맵핑메서드 showArticle 추가
- 요청시 articleId에 대한 Article을 찾아서 Model에 담음
- "/articles/articleShow" 반환
- 동적으로 게시글을 보이는 `/articles/articleShow.html` 작성

## 2.11 home에서 게시글 이름 클릭 시 게시글로 이동할 수 있도록 함(목록 기능 구현 완료)

- home.html에서, 게시글에 대한 a태그의 주솟값을 동적으로 생성하도록 했다.
- 현재는 게시글이 스스로의 Id를 가지고 있지 않기 때문에 list의 반복 순서 기준으로 맵핑하도록 했다.

## 2.12 Article의 LocalDate -> LocalDateTime

- 게시글 작성일자 부분에서 현재 시각에 대한 요구사항까지 추가됨
- 기존의 LocalDate 필드를 LocalDateTime으로 변경함

---

# Step3 DB에 저장하기

- [x] H2 데이터베이스 의존성 추가 및 연동
- [x] 게시글 데이터 저장(DB 연동)
- [x] 게시글 목록 구현(DB 연동)
- [x] 게시글 상세보기 구현(DB 연동)
- [x] 사용자 정보 DB 저장
- [ ] 배포

---

## 3.01 Gradle 의존 라이브러리 추가

- jdbc 라이브러리 추가
- h2 데이터베이스 관련 라이브러리 추가

## 3.02 h2 데이터베이스 연동

- h2 데이터베이스 설정 추가

## 3.03 UserRepository 구현 분리

- 데이터베이스 연동을 위해 확장 가능성을 열어야한다.
- `UserRepository`를 인터페이스로 하고, 기존 사용하던 것은 구현체 MemoryUserRepository로 하였다.
- 의존 계층에서 역할을 의존하도록 했다.
- 이제부터 외부에서는 데이터 저장계층의 역할인 userRepository를 의존한다. 구현체의 상태가 바뀌어도 외부의 코드를 변경할 필요가 없다.

## 3.04 회원 등록시 중복 검증책임을 UserService에게 넘김

- UserRepository 구현체를 다른 구현체로 바꾸기엔, 기존의 UserRepository에서 수행해야 했던 중복 검증 로직을 또 다시 구현해야한다.
- 이 책임을 UserService에게 위임하고, 데이터 접근계층인 UserRepository는 순수하게 값을 보관하도록 한다.
- 이런 검증에 대한 테스트 코드는 이미 UserService에서 별도로 작성해두기도 했고, MemoryUserRepository에서 별도로 작성할 필요가 없으니 제거했다.

## 3.05 JdbcUserRepository 추가 및 의존 관계 변경

- 순수 Jdbc 방식의 JdbcUserRepository를 생성
- UserRepository 구현체 변경 : MemoryUserRepository -> JdbcUserRepository
- User 테이블에 대한 DDL SQL을 별도로 루트 경로 하위의 sql폴더에 저장했다.

## 3.06 Article에 id 부여, ArticleRepository 내부 구조 변경

- 데이터베이스에 연동하기 위해서는 게시글이 스스로 글 번호를 가지고 있어야한다.
- 게시글에게 id를 부여하고, 스스로 가진 id를 기준으로 접근할 수 있도록 ArticleRepository 내부적으로는 ConcurrentHashMap으로 구조 변경

## 3.07 역할 : ArticleRepository / 구현 : MemoryArticleRepository 분리

- 데이터베이스 연동의 확장성을 위해 ArticleRepository 인터페이스를 따로 분리함
- 유효한 게시글인지 여부의 검증은 ArticleController 단에서 하도록 함
- 고로, ArticleRepository의 검증 로직이 제대로 수행되는지에 대한 테스트는 제거했다.

## 3.08 JdbcArticleRepository 추가 및 의존 관계 변경

- 순수 Jdbc 방식의 JdbcArticleRepository를 생성
- ArticleRepository 구현체 변경 : MemoryArticleRepository -> JdbcArticleRepository
- article 테이블에 대한 DDL SQL을 별도로 루트 경로 하위의 sql폴더에 저장했다.

## 3.09 ArticleService 분리

- Article 등록, 검증 등에 대한 로직을 ArticleService 계층에 분리했다.
- ArticleeController는 파라미터의 해석 및 뷰테 무엇을 넘길 것인지에 대해서만 초점을 두게 하고, ArticleService는 검증 비즈니스 로직을 수행하도록 한다.

## 3.10  JdbcTemplateUserRepository 추가 및 의존관계 변경

- JdbcTemplate 방식의 JdbcTemplateUserRepository를 생성
- UserRepository 구현체 변경 : JdbcUserRepository -> JdbcTemplateUserRepository

## 3.11 JdbcArticleRepository - 등록 article에 id를 set하지 않던 문제 수정

- JdbcArticleRepository에서 save시 id를 db상에선 등록하였으나 등록 객체에 set하지 않았던 문제를 확인, 수정

## 3.12 JdbcTemplateArticleRepository 추가 및 의존관계 변경

- JdbcTemplate 방식의 JdbcTemplateArticleRepository를 생성
- ArticleRepository 구현체 변경 : JdbcArticleRepository -> JdbcTemplateArticleRepository

## 3.13 Repository 계층을 엔티티와 같은 패키지에 둠

- 엔티티(데이터베이스에 맞닿는 객체)의 상태 변경은 레포지토리에서 이루어지므로, 이들을 같은 패키지에서 두는게 맞다는 생각에 같은 패키지에 두었다.

## 3.14 User DDL 수정 - UNIQUE 제약 추가

> UNIQUE (user_name, user_email)
- 사용자 이름, 이메일에 유니크 제약 추가

## 3.15 User 클래스 - Setter 제거, Builder패턴 적용

- User클래스에서 Setter를 제거했다.
- 생성을 한번에 하고 Builder 패턴을 적용하여 생성시 매개변수, 인자를 읽기 편하게 하였다.
- 만약 변경을 해야한다면 setter 비슷한 역할을 하는 메서드를 생성해야하는데 이는 package-private로 처리할 예정이다. (Repository)
- 가입 시, 외부에서 LocalDate를 주입했는데 생성시 자동 초기화되도록 했다.

## 3.16 UserRepository 테스트 - 스프링 연동

- UserRepository 테스트를 스프링부트 연동으로, 의존관계를 주입하여 하도록 함
- @Transactional 어노테이션을 통해, 테스트할 때마다 롤백되도록 함
- 기존의 MemoryUserRepositoryTest는 제거

## 3.17 회원 가입시 폼의 내용이 UserJoinRequest에 맵핑되도록 함

- 회원 가입시 바로 엔티티를 생성하지 않고 UserJoinRequest에 맵핑되도록 했다.
- Service 계층에서는 UserJoinRequest를 인자로 하여 생성하도록 했다.

## 3.18 URL 변경 - "users/new" -> "user/join"

- 회원 가입 URL에서 전달되는 의미가 모호한 것 같아서, "user/join"으로 변경했다.

## 3.19 유저 생성시, 선 검증 후 유저 엔티티 생성하도록 코드 순서 변경

- UserJoinRequest를 검증후, 엔티티 생성하도록 코드 순서를 변경했다.

## 3.20 URL 변경 - "articles/new" -> "articles/write"

- 글 작성 URL에서 전달되는 의미가 모호한 것 같아서, "articles/write"으로 변경했다.

## 3.21 Article - Setter 제거 및 글작성 요청 ArticleWriteRequest 맵핑

1. setter 제거
   - Article클래스에서 Setter를 웬만한건 다 제거했다.
   - 아이디 초기화 해주는 부분은 setter가 필요한데 이 부분은 package-private로 지정하고 initArticleId로 생성함
   - 생성을 한번에 하고 Builder 패턴을 적용하여 생성시 매개변수, 인자를 읽기 편하게 하였다.

2. 글 작성 요청 -> ArticleWriteRequest 맵핑
   - 가입 시, 외부에서 디폴트 작성자 및 LocalDateTime를 주입했는데 리퀘스트 생성시 자동 초기화되도록 했다.
   - 글 작성시 바로 엔티티를 생성하지 않고 ArticleWriteRequest에 맵핑되도록 했다.
   - Service 계층에서 ArticleWriteRequest를 기반으로 엔티티를 생성함.
   - 이후 제어 흐름상 어떤 검증이나 중간 로직이 필요할 때, Request를 기반으로 검증하면 된다.

## 3.22 - User 생성을 빌더를 통해서만 가능하도록 함

- 요청 맵핑은 이제 UserJoinRequest를 통해서만 이루어지므로 생성자 호출을 통한 생성이 불필요하다.
- 빌더를 통해서만 생성할 수 있음.

## 3.23 - ArticleRepositoryTest 스프링부트 연동

- MemoryArticleRepositoryTest -> ArticleRepositoryTests
- 의존관계 주입을 SpringBoot 연동으로 하여 자동으로 하도록 함
- 테스트를 트랜잭션 단위로 함

## 3.24 userList, Profile 요청 시 UserResponse로 변환하여 반환

- 기존엔 유저 엔티티를 그대로 Model에 담아 반환했는데, UserResponse로 변환하여 반환하도록 했다.
- response에는 순전히 getter만 두도록 했다.

## 3.25 request, response 객체들을 별도로 패키지에 분리

- request, response 객체가 많아져서, controller 패키지가 너무 뚱뚱해지고 있다.
- 내부적으로 따로 분리해버렸다.

## 3.26 ArticleResponse 분리

- Model에 Article 엔티티를 그대로 담지 않고, ArticleResponse로 변환하여 반환하도록 했다.

---