
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

---
