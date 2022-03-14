# 2022 Java Spring Cafe

2022년도 마스터즈 멤버스 백엔드 스프링 카페 프로젝트

## 1단계

<details>
    <summary>1단계 요구사항</summary>

### 회원 가입 기능 구현

- url : `POST 요청 - /users`
- [x] 가입하기 페이지에서 회원 가입 폼을 표시한다.
- [x] 개인정보를 입력하고 확인을 누르면 회원 목록 조회 페이지로 이동한다.

#### 세부 요구사항

- [x] 가입하기 페이지는 static/user/form.html을 사용한다.
- [x] static에 있는 html을 templates로 이동한다.
- [x] 사용자 관리 기능 구현을 담당할 UserController를 추가하고 애노테이션 매핑한다.
    - [x] @Controller 애노테이션 추가
- [x] 회원가입하기 요청(POST 요청)을 처리할 메소드를 추가하고 매핑한다.
    - [x] @PostMapping 추가하고 URL 매핑한다.
- [x] 사용자가 전달한 값을 User 클래스를 생성해 저장한다.
    - [x] 회원가입할 때 전달한 값을 저장할 수 있는 필드를 생성한 후 setter와 getter 메소드를 생성한다.
- [x] 사용자 목록을 관리하는 ArrayList를 생성한 후 앞에서 생성한 User 인스턴스를 ArrayList에 저장한다.
- [x] 사용자 추가를 완료한 후 사용자 목록 페이지("redirect:/users")로 이동한다.

### 회원 목록 조회 기능 구현

- url : `GET 요청 - /users`
- [x] 목록 조회 페이지에서는 가입한 회원들의 목록을 출력한다.

#### 세부 요구사항

- [x] 회원목록 페이지는 static/user/list.html을 사용한다.
- [x] static에 있는 html을 templates로 이동한다.
- [x] Controller 클래스는 회원가입하기 과정에서 추가한 UserController를 그대로 사용한다.
- [x] 회원목록 요청(GET 요청)을 처리할 메소드를 추가하고 매핑한다.
    - [x] @GetMapping을 추가하고 URL 매핑한다.
- [x] Model을 메소드의 인자로 받은 후 Model에 사용자 목록을 users라는 이름으로 전달한다.
- [x] 사용자 목록을 user/list.html로 전달하기 위해 메소드 반환 값을 "user/list"로 한다.
- [x] user/list.html 에서 사용자 목록을 출력한다.

### 회원 프로필 조회 기능 구현

- url : `GET 요청 - /users/{userId}`
- [x] 회원 프로필 페이지에서는 개별 회원의 프로필 정보를 출력한다.

#### 세부 요구사항

- [x] 회원 프로필 보기 페이지는 static/user/profile.html을 사용한다.
- [x] static에 있는 html을 templates로 이동한다.
- [x] 앞 단계의 사용자 목록 html인 user/list.html 파일에 닉네임을 클릭하면 프로필 페이지로 이동하도록 한다.
    - [x] html에서 페이지 이동은 <a /> 태그를 이용해 가능하다.
    - [x] <a href="/users/{{userId}}" />와 같이 구현한다.
- [x] Controller 클래스는 앞 단계에서 사용한 UserController를 그대로 사용한다.
- [x] 회원프로필 요청(GET 요청)을 처리할 메소드를 추가하고 매핑한다.
    - [x] @GetMapping을 추가하고 URL 매핑한다.
    - [x] URL은 "/users/{userId}"와 같이 매핑한다.
- [x] URL을 통해 전달한 사용자 아이디 값은 @PathVariable 애노테이션을 활용해 전달 받을 수 있다.
- [x] ArrayList에 저장되어 있는 사용자 중 사용자 아이디와 일치하는 User 데이터를 Model에 저장한다.
- [x] user/profile.html 에서는 Controller에서 전달한 User 데이터를 활용해 사용자 정보를 출력한다.

</details>

<details>
  <summary>1단계 피드백 사항</summary>
  
- [ ] Autowired가 생략 가능한 경우, 이유에 대해 학습하기
- [ ] eqauls, hashcode 메서드를 올바르게 사용하는 방법 학습하기
- [ ] Optional의 `get()`??
- [ ] Dto에서 getter를 사용하지 않고 도메인 객체 만들기
- [ ] 네이밍 신경쓰기
</details>


## 2단계
<details>
  <summary>2단계 요구사항</summary>

### 기능 요구사항
- [ ] 사용자는 게시글을 작성할 수 있어야 한다.
- [ ] 모든 사용자는 게시글 목록을 볼 수 있어야 한다.
- [ ] 모든 사용자는 게시글 상세 내용을 볼 수 있어야 한다.
- [ ] (선택) 사용자 정보를 수정할 수 있어야 한다.

### 프로그래밍 요구사항
#### 글쓰기
- [ ] 게시글 페이지는 static/qna/form.html을 수정해서 사용한다.
- [ ] static에 있는 html을 templates로 이동한다.
- [ ] 게시글 기능 구현을 담당할 ArticleController를 추가하고 애노테이션 매핑한다.
- [ ] 게시글 작성 요청(POST 요청)을 처리할 메소드를 추가하고 매핑한다.
- [ ] 사용자가 전달한 값을 Article 클래스를 생성해 저장한다.
- [ ] 게시글 목록을 관리하는 ArrayList를 생성한 후 앞에서 생성한 Article 인스턴스를 ArrayList에 저장한다.
- [ ] 게시글 추가를 완료한 후 메인 페이지(“redirect:/”)로 이동한다.

#### 글 목록 조회하기
- [ ] 메인 페이지(요청 URL이 “/”)를 담당하는 Controller의 method에서 게시글 목록을 조회한다.
- [ ] 조회한 게시글 목록을 Model에 저장한 후 View에 전달한다. 게시글 목록은 앞의 게시글 작성 단계에서 생성한 ArrayList를 그대로 전달한다.
- [ ] View에서 Model을 통해 전달한 게시글 목록을 출력한다.
  - [ ] * 게시글 목록을 구현하는 과정은 사용자 목록을 구현하는 html 코드를 참고한다.

#### 게시글 상세보기
- [ ] 게시글 목록(qna/list.html)의 제목을 클릭했을 때 게시글 상세 페이지에 접속할 수 있도록 한다.
  - [ ] 게시글 상세 페이지 접근 URL은 "/articles/{index}"(예를 들어 첫번째 글은 /articles/1)와 같이 구현한다.
  - [ ] 게시글 객체에 id 인스턴스 변수를 추가하고 ArrayList에 게시글 객체를 추가할 때 ArrayList.size() + 1을 게시글 객체의 id로 사용한다.
- [ ] Controller에 상세 페이지 접근 method를 추가하고 URL은 /articles/{index}로 매핑한다.
- [ ] ArrayList에서 index - 1 해당하는 데이터를 조회한 후 Model에 저장해 /qna/show.html에 전달한다.
- [ ] /qna/show.html에서는 Controller에서 전달한 데이터를 활용해 html을 생성한다.

</details>