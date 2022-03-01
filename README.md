# 2022 Java Spring Cafe

2022년도 마스터즈 멤버스 백엔드 스프링 카페 프로젝트

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


