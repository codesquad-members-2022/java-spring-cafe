# 2022 Java Spring Cafe

2022년도 마스터즈 멤버스 백엔드 스프링 카페 프로젝트

## 미션 1 - 회원 가입 및 목록 기능

### 요구사항

- [x] 회원 가입 기능 구현
  - 회원 가입 양식: /users/form.html
  - 요청 메서드: POST /users
  - 전달받은 값으로 User 객체를 생성하여 저장
    - getter, setter 메소드를 생성한다
  - 생성된 User 객체는 ArrayList로 구현한 사용자 목록에 저장한다.
  - 회원 가입 후: 유저 목록으로 리다이렉트 (redirect:/users)
- [x] 회원 조회 기능 구현
  - 요청 메서드: GET /users
  - 회원 목록 뷰 템플릿: /users/list.html
- [x] 회원 프로필 조회 기능 구현
  - 회원 목록에서 닉네임을 누르면 프로필 페이지로 이동하도록 한다. 
  - 요청 메서드: GET /users/{userId}
  - 회원 프로필 뷰 템플릿: /users/profile.html
- [x] 템플릿을 이용하여 HTML 파일들의 중복 제거
- [x] DB는 사용하지 않는다.

### 구현과정 

- LocalRMIServerSocketFactory 클래스에서 IOException 발생
  - 상황
    - 초기 상태의 스프링 부트 프로젝트를 실행시켰을 때
    - WARN 로그를 통해 `sun.rmi.transport.tcp` 패키지에서 IOExcetion 예외가 보고된다.
  - 예외 메시지 
    - The server sockets created using the LocalRMIServerSocketFactory only accept connections from clients running on the host where the RMI remote objects have been exported.
  - 해결방법
    - CLI argument(인텔리제이 실행 설정에서는 VM options 항목)로 `-Dcom.sun.management.jmxremote.local.only=false`를 넘겨주면 예외가 발생하지 않늗다.
- `@EnabaleWebMvc`
  - 인터넷 상의 스프링 예제에는 `WebMvcConfigurer`의 구현체 클래스에 이 애너테이션이 붙은 경우가 자주 있다. 
  - 이 애너테이션을 사용하면 뷰 생성시 스태틱 리소스들을 제대로 불러오지 못하는 현상이 생겼다. 
  - 스프링 부트의 스프링 MVC 자동 설정을 비활성화시키기 때문이라고 하는데, 정확한 내용은 확인이 필요하다.

## 미션 2 - 글 쓰기 기능 구현

### 요구사항

- [x] 사용자는 게시글을 작성할 수 있어야 한다.
  - 게시글 작성 뷰 템플릿: /qna/form.html
  - 요청 메서드: POST /questions
  - 글 등록 후: 글 목록으로 이동 (redirect:/)
  - ArticleController를 작성하여 요청 처리
  - 전달받은 값으로 Article 객체 생성
  - Article 객체는 ArrayList에 저장
- [x] 모든 사용자는 게시글 목록을 볼 수 있어야 한다.
  - 메인 페이지(/)에서 게시글 목록 조회
  - 게시글 목록은 ArrayList를 그대로 전달
- [x] 모든 사용자는 게시글 상세 내용을 볼 수 있어야 한다.
  - 게시글 목록의 제목을 클릭했을 때 게시글 상세 내용 표시
  - 요청 메서드: GET /articles/{index}
  - 뷰 템플릿: /qna/show.html
  - index 값은 ArrayList 내부 인덱스 + 1로 한다 (i.e. 1부터 시작한다).
- [x] (선택) 사용자 정보를 수정할 수 있어야 한다.
  - 변경가능: 비밀번호, 이름, 이메일 / 변경불가: 유저ID
  - 비밀번호가 일치해야 변경 가능하다
  - 회원정보 수정 화면
    - 회원 목록(/users)에서 진입
    - 요청 메서드: GET /users/{id}/form
    - 뷰 템플릿: /users/updateForm.html (/users/form.html을 복사하여 작성할 것)
    - 기존 정보를 input 기본값(value 속성)으로 전달
  - 회원정보 수정
    - 요청 메서드: PUT /users/{id}/update
    - UserRepository에서 id가 일치하는 사용자를 조회하고 save() 메서드로 업데이트한다.
    - 수정 후 유저 목록으로 이동 (redirect:/users)

### 구현과정 

- 스프링 부트 2.2 업데이트 이후로 `application.properties` 파일에 `spring.mvc.hiddenmethod.filter.enabled=true`를 추가해야 브라우저가 form의 히든값으로 put 요청을 보내는 것을 인식할 수 있다. 

## 미션 3 - DB에 저장하기

### 요구사항

- [x] H2 데이터베이스 의존성을 추가하고 연동한다.
  - Spring JDBC를 사용한다.
  - DB 저장 및 조회에 필요한 SQL은 직접 작성한다.
  - ORM은 사용하지 않는다.
- [x] 게시글 데이터 저장하기
  - Article 클래스를 DB 테이블에 저장할 수 있게 구현한다.
  - Article 테이블이 적절한 Primary Key를 가지도록 구현한다.
- [x] 게시글 목록 구현하기
  - 전체 게시글 목록 데이터를 DB에서 조회하도록 구현한다.
- [x] 게시글 상세보기 구현하기
  - 게시글의 세부 내용을 DB에서 가져오도록 구현한다.
- [x] 사용자 정보 DB에 저장
  - 회원가입을 통해 등록한 사용자 정보를 DB에 저장한다.
- [x] Heroku로 배포를 진행한다. 
  - README에 배포 URL을 기술한다.
  - https://astraum-spring-cafe.herokuapp.com/

### 전단계 피드백 반영

- [x] UserService::validateNotNull을 제거한다
- [ ] User 중복에 대한 검증에 Validator를 사용하고 UserService의 책임과 분리한다.
  - Validator에 대해 학습한다. 
  - 중복에 대한 검증을 Repository 또는 DB 쪽에서 처리하는 방법을 찾아본다. 

### 구현과정

- 인텔리제이에서 실행했을 때에는 문제가 없는데 Heroku에서는 문제가 되는 경우가 있다.
  - Mustache partial 템플릿의 경로 맨 앞에 역슬래시가 붙어 있으면 참조 위치가 templates//{경로}가 되어 오류가 난다. 
  - 컨트롤러에서 뷰 이름 맨 앞에 역슬래시가 붙어 있으면 템플릿이 아니라 URL로 인식하여 리다이렉션 또는 404 오류가 난다.
- Heroku에 스프링 부트 앱을 push할 때 기본 설정은 자바 1.8 기준이다. `systems.properties` 설정 파일을 만들고 `java.runtime.version=11` 설정을 추가하였다. 
- 의존성이 포함되지 않은 *plain.jar 파일로 인해 앱이 정상적으로 실행되지 않는 현상이 있었다. `build.gradle` 파일에 `jar {enabled = false}` 설정을 추가하여 *plain.jar 파일이 생성되지 않게 하였다.
  - bootJar 및 jar의 classifier 속성을 바꾸어 bootJar로 생성된 executable jar가 우선적으로 실행되게 하는 방식으로도 해결할 수 있다. (https://docs.spring.io/spring-boot/docs/2.5.1/gradle-plugin/reference/htmlsingle/#packaging-executable.and-plain-archives) 
  - Procfile을 만들고 어느 jar를 실행시킬 것인지 경로를 명시하여 해결하는 방법도 있다 (https://devcenter.heroku.com/articles/deploying-gradle-apps-on-heroku#the-procfile)
- timestamp가 서울 시간 기준으로 생성되도록 config vars에 (키=TZ, 값=Asia/Seoul)을 추가하였다.
- Heroku는 H2 데이터베이스 애드온을 지원하지 않는다. 
  - db 파일을 프로젝트 폴더에 포함시켜서 같이 push하거나 spring.sql.init 계열 설정을 이용하여 테이블과 초기 데이터를 세팅해 줄 수는 있지만, 웹 상에서 저장하거나 변경한 데이터가 지속되지 않고 일정 주기로 초기화된다.
  - H2 대신 PosgreSQL 등 Heroku가 지원하는 DB를 사용하거나, 만약 필요하다면 별도의 h2 서버를 띄워서 해결할 수도 있을 것이다.

## 미션 4 - 로그인 구현

### 요구사항

- [x] HttpSession을 이용하여 로그인, 로그아웃을 구현한다. 
  - 로그인 상태에서는 상단 메뉴에 로그아웃, 개인정보수정 버튼을 표시한다. 
  - 로그아웃 상태에서는 상단 메뉴에 로그인, 회원가입 버을 표시한다. 
  - Spring MVC 메서드의 인자로 HttpSession을 이용한다.
  - Spring Security 등 별도의 라이브러리를 사용하지 않는다. 
  - 템플릿 기반으로 구현한다. 
- [ ] (선택) 로그인한 사용자는 자신의 정보를 수정할 수 있어야 한다. 
  - 다른 사용자의 정보를 수정하려고 하면 에러 페이지를 출력한다. 

### 구현과정

- `application.properties`
  - `server.servlet.session.tracking-modes=cookie` 세션 추적 방식을 cookie로 설정한다. 이 설정을 파일에 추가하지 않아도 기본적으로 cookie 방식으로 동작했다.
  - `spring.mustache.expose-session-attributes=true` Mustache 엔진이 현재 세션의 내용에 접근할 수 있게 허용한다. MVC 메서드에서 세션 내용을 모델로 추가하지 않아도 템플릿에서 세션 내용에 바로 접근이 가능해진다. 
- 로그아웃은 `GET` 요청으로도 구현 가능하나 로그아웃은 상태를 바꾸는 행위이므로 `POST`가 더 적절하다고 판단하였다. a 태그는 `POST` 요청을 보낼 수 없기 때문에 JS 함수를 onclick 이벤트로 삽입하여 구현하였다. 
