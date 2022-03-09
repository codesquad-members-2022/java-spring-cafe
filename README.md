# 2022 Java Spring Cafe

## 2022년도 마스터즈 멤버스 백엔드 스프링 카페 프로젝트

### 회원가입 기능 구현
- [X] 가입하기 페이지는 static/user/form.html을 사용
- [X] static에 있는 html을 templates로 이동
- [X] 사용자 관리 기능 구현을 담당할 UserController를 추가하고 애노테이션 매핑
- [X] @Controller 애노테이션 추가
- [X] 회원가입하기 요청(POST 요청)을 처리할 메소드를 추가하고 매핑
- [X] @PostMapping 추가하고 URL 매핑
- [X] 사용자가 전달한 값을 User 클래스를 생성해 저장
- [X] 회원가입할 때 전달한 값을 저장할 수 있는 필드를 생성한 후 setter와 getter 메소드를 생성
- [X] 사용자 목록을 관리하는 ArrayList를 생성한 후 앞에서 생성한 User 인스턴스를 ArrayList에 저장
- [X] 사용자 추가를 완료한 후 사용자 목록 페이지("redirect:/users")로 이동

### 회원목록 기능 구현
- [X] 회원목록 페이지는 static/user/list.html을 사용
- [X] static에 있는 html을 templates로 이동
- [X] Controller 클래스는 회원가입하기 과정에서 추가한 UserController를 그대로 사용
- [X] 회원목록 요청(GET 요청)을 처리할 메소드를 추가하고 매핑
- [X] @GetMapping을 추가하고 URL 매핑
- [X] Model을 메소드의 인자로 받은 후 Model에 사용자 목록을 users라는 이름으로 전달
- [X] 사용자 목록을 user/list.html로 전달하기 위해 메소드 반환 값 "user/list"
- [X] user/list.html 에서 사용자 목록을 출력

### 회원 프로필 정보보기
- [X] 회원 프로필 보기 페이지는 static/user/profile.html을 사용
- [X] static에 있는 html을 templates로 이동
- [X] 앞 단계의 사용자 목록 html인 user/list.html 파일에 닉네임을 클릭하면 프로필 페이지로 이동
- [X] <a href="/users/{{userId}}" />와 같이 구현
- [X] Controller 클래스는 앞 단계에서 사용한 UserController를 그대로 사용
- [X] 회원프로필 요청(GET 요청)을 처리할 메소드를 추가하고 매핑
- [X] @GetMapping을 추가하고 URL 매핑
- [X] URL은 "/users/{userId}"와 같이 매핑
- [X] ArrayList에 저장되어 있는 사용자 중 사용자 아이디와 일치하는 User 데이터를 Model에 저장
- [X] user/profile.html 에서는 Controller에서 전달한 User 데이터를 활용해 사용자 정보를 출력

### HTML의 중복 제거
- [X] index.html, /user/form.html, /qna/form.html 코드를 보면 header, navigation bar, footer 부분에 많은 중복 코드가 있다. 중복 코드를 제거