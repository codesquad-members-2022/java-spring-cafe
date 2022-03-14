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

- [ ] 사용자는 게시글을 작성할 수 있어야 한다.
  - 게시글 작성 뷰 템플릿: /qna/form.html
  - 요청 메서드: POST /questions
  - 글 등록 후: 글 목록으로 이동 (redirect:/)
  - ArticleController를 작성하여 요청 처리
  - 전달받은 값으로 Article 객체 생성
  - Article 객체는 ArrayList에 저장
- [ ] 모든 사용자는 게시글 목록을 볼 수 있어야 한다.
  - 메인 페이지(/)에서 게시글 목록 조회
  - 게시글 목록은 ArrayList를 그대로 전달
- [ ] 모든 사용자는 게시글 상세 내용을 볼 수 있어야 한다.
  - 게시글 목록의 제목을 클릭했을 때 게시글 상세 내용 표시
  - 요청 메서드: GET /articles/{index}
  - 뷰 템플릿: /qna/show.html
  - index 값은 ArrayList 내부 인덱스 + 1로 한다 (i.e. 1부터 시작한다).
- [ ] (선택) 사용자 정보를 수정할 수 있어야 한다.
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
