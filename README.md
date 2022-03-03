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
