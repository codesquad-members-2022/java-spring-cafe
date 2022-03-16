# 2022 Java Spring Cafe

2022년도 마스터즈 멤버스 백엔드 스프링 카페 프로젝트

## 1 단계 - 회원 가입 및 목록 기능
### 기능 요구 사항
- [x] 회원 가입 기능 구현
- [x] 회원 목록 조회 기능 구현
- [x] 회원 프로필 조회 기능 구현
### 프로그래밍 요구 사항
- [x] 각 기능에 따른 url 과 메소드 convention
- **회원 가입 기능 구현**
  - [x] 가입하기 페이지는 `static/user/form.html` 을 사용
  - [x] `static`에 있는 `html`을 `templates`로 이동
  - [x] 사용자 관리 기능 구현을 담당할 `UserController` 를 추가하고 애노테이션 매핑
  - [x] 회원가입하기 요청(`POST`)을 처리할 메소드를 추가하고 매핑
  - [x] `@PostMapping` 추가하고 `URL` 매핑
  - [x] 사용자가 전달한 값을 `User` 클래스를 생성해 저장
  - [x] 회원가입할 때 전달한 값을 저장할 수 있는 필드를 생성한 후 setter 와 getter 메소드를 생성
  - [ ] 사용자 목록을 관리하는 `ArrayList` 를 생성한 후 앞에서 생성한 `User 인스턴스`를 `ArrayList` 에 저장  
        ㄴ `HashMap` 으로 대체
  - [x] 사용자 추가를 완료한 후 사용자 목록 페이지(`"redirect:/users"`)로 이동

- **회원 목록 기능 구현**
  - [x] 회원목록 페이지는 `static/user/list.html` 을 사용
  - [x] `static` 에 있는 `html` 을 `templates` 로 이동
  - [x] `Controller` 클래스는 회원가입하기 과정에서 추가한 `UserController` 를 그대로 사용
  - [x] 회원목록 요청(`GET`)을 처리할 메소드를 추가하고 매핑
  - [x] `@GetMapping` 을 추가하고 `URL` 매핑한다. 
  - [x] `Model`을 메소드의 인자로 받은 후 `Model`에 사용자 목록을 `users`라는 이름으로 전달
  - [x] 사용자 목록을 `user/list.html` 로 전달하기 위해 메소드 반환 값을 `"user/list"` 로 지정
  - [x] `user/list.html` 에서 사용자 목록을 출력

- **회원 프로필 정보보기**
  - [x] 회원 프로필 보기 페이지는 `static/user/profile.html` 을 사용
  - [x] `static` 에 있는 `html` 을 `templates` 로 이동
  - [x] 앞 단계의 사용자 목록 `html` 인 `user/list.html` 파일에 닉네임 클릭하면 프로필 페이지로 이동
  - [x] `html` 에서 페이지 이동은 `<a /> 태그`를 사용
  - [x] `Controller` 클래스는 앞 단계에서 사용한 `UserController` 를 그대로 사용
  - [x] 회원프로필 요청(`GET`)을 처리할 메소드를 추가하고 매핑
  - [x] `@GetMapping` 을 추가하고 `URL` 매핑
  - [x] `URL` 은 `"/users/{userId}"`와 같이 매핑
  - [x] `URL`을 통해 전달한 사용자 아이디 값은 `@PathVariable` 애노테이션을 활용해 전달 받기
  - [x] `ArrayList` 에 저장되어 있는 사용자 중 사용자 아이디와 일치하는 `User` 데이터를 `Model`에 저장
  - [x] `user/profile.html` 에서는 `Controller`에서 전달한 User 데이터를 활용해 사용자 정보 출력

## 2 단계 - 회원 가입 및 목록 기능
### 기능 요구 사항
- [x] 게시글 작성 기능
- [x] 게시글 목록 조회 기능
- [x] 게시글 상세 내용 조회 기능
- [x] (선택) 사용자 정보 수정 기능
### 프로그래밍 요구 사항
- **글쓰기**
  - [x] 게시글 페이지는 `static/qna/form.html` 을 수정해서 사용
  - [x] `static` 에 있는 `html` 을 `templates` 로 이동
  - [x] 게시글 기능 구현을 담당할 `ArticleController` 를 추가하고 애노테이션 매핑
  - [x] 게시글 작성 요청(POST)을 처리할 메소드를 추가하고 매핑
  - [x] 사용자가 전달한 값을 `Article` 클래스를 생성해 저장
  - [x] 게시글 목록을 관리하는 `ArrayList` 를 생성한 후 앞에서 생성한 `Article` 인스턴스를 `ArrayList` 에 저장
  - [x] 게시글 추가를 완료한 후 메인 페이지(`“redirect:/”`)로 이동

- **글 목록 조회하기**
  - [x] 메인 페이지(요청 URL 이 `“/”`)를 담당하는 `Controller` 의 `method` 에서 게시글 목록을 조회
  - [x] 조회한 게시글 목록을 `Model` 에 저장한 후 `View` 에 전달 
  - [x] 게시글 목록은 앞의 게시글 작성 단계에서 생성한 `ArrayList` 를 그대로 전달
  - [x] `View` 에서 `Model` 을 통해 전달한 게시글 목록을 출력

- **게시글 상세보기**
  - [x] 게시글 목록(`qna/list.html`)의 제목을 클릭했을 때 게시글 상세 페이지에 접속
  - [x] 게시글 상세 페이지 접근 `URL` 은 `"/articles/{index}"`와 같이 구현
  - [x] 게시글 객체에 id 인스턴스 변수를 추가하고 `ArrayList`에 게시글 객체를 추가할 때 `ArrayList.size() + 1` 을 게시글 객체의 `id` 로 사용
  - [x] `Controller` 에 상세 페이지 접근 `method` 를 추가하고 `URL` 은 `/articles/{index}` 로 매핑
  - [x] `ArrayList`에서 `index - 1` 해당하는 데이터를 조회한 후 `Model` 에 저장해 `/qna/show.html` 에 전달
  - [x] `/qna/show.html` 에서는 `Controller` 에서 전달한 데이터를 활용해 html 생성

- **회원 정보 수정**
  - [x] 회원가입한 사용자의 정보를 수정 가능
  - [x] 비밀번호, 이름, 이메일만 수정할 수 있으며, 사용자 아이디는 수정 불가
  - [x] 비밀번호가 일치하는 경우에만 수정 가능
- 수정화면
  - [x] 회원가입한 사용자 정보를 수정할 수 있는 수정 화면과 사용자가 수정한 값을 업데이트할 수 있는 기능을 나누어 개발
  - [x] `/user/form.html` 파일을 `/user/updateForm.html` 로 복사한 후 수정화면을 생성

- 회원 정보 수정

  - [x] UserController 의 사용자가 수정한 정보를 User 클래스에 저장
  - [x] `{id}` 에 해당하는 User 를 DB 에서 조회
  - [x] DB 에서 조회한 User 데이터를 새로 입력받은 데이터로 업데이트
  - [ ] UserRepository 의 `save()` 메소드를 사용해 업데이트

## 3 단계 - DB에 저장하기
### 프로그래밍 요구 사항
- [x] H2 데이터베이스 연동
  - [x] H2 데이터베이스 의존성 추가 및 연동
  - [x] ORM 은 사용하지 않기
  - [x] Spring JDBC 사용
  - [x] DB 저장 및 조회에 필요한 SQL문 직접 작성

- [x] 게시글 데이터 저장하기
  - [x] Article 클래스를 DB 테이블에 저장할 수 있게 구현
  - [x] Article 테이블이 적절한 PK를 가지도록 구현

- [x] 게시글 목록 구현하기
  - [x] 전체 게시글 목록 데이터를 DB 에서 조회하도록 구현

- [x] 게시글 상세보기 구현하기
  - [x] 게시글의 세부 내용을 DB 에서 가져오도록 구현

- [x] 사용자 정보 DB에 저장
  - [x] 회원가입을 통해 등록한 사용자 정보를 DB에 저장
  
- [x] 배포
  - [x] Heroku 로 배포를 진행하고 README 에 배포 URL 기술

## 4단계 - 로그인 구현
### 기능요구사항
- [x] 로그인과 로그아웃 기능이 정상 동작
- [x] 현재 상태가 로그인 상태이면 상단 메뉴에서 '로그아웃', '개인정보수정'이 표시
- [x] 현재 상태가 로그인 상태가 아니라면 상단 메뉴에서 '로그인', '회원가입'이 표시
### 프로그래밍 요구사항
- [x] Spring MVC 에서 메소드의 인자로 HttpSession 을 이용해서 로그인 구현
- [x] Spring security 와 같은 별도의 라이브러리 등은 사용하지 않기
- [x] API 가 아닌 템플릿 기반으로 구현
### 추가 요구 사항
- [x] 개인정보 수정 기능 추가
  - [x] 로그인한 사용자는 자신의 정보 수정 가능
  - [ ] 이름, 이메일만 수정 가능
  - [x] 사용자 아이디는 수정 불가
  - [x] 비밀번호 일치하는 경우만 수정 가능

- 배포 링크
  - https://java-spring-cafe-bugpigg.herokuapp.com/
