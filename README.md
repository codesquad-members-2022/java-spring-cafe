# 2022 Java Spring Cafe

2022년도 마스터즈 멤버스 백엔드 스프링 카페 프로젝트

# Step 1 : 회원 가입 및 목록 기능

<details>
<summary> 🖼📝 Step 1 결과와 설명 </summary>
<div markdown="1">

<details>
<summary> 요구사항 </summary>
<div markdown="1">


### 회원가입 기능 구현

- [X] 가입하기 페이지는 static/user/form.html을 사용한다.
- [X] static에 있는 html을 templates로 이동한다.
- [X] 사용자 관리 기능 구현을 담당할 UserController를 추가하고 애노테이션 매핑한다.
    - @Controller 애노테이션 추가
- [X] 회원가입하기 요청(POST 요청)을 처리할 메소드를 추가하고 매핑한다.
    - @PostMapping 추가하고 URL 매핑한다.
- [X] 사용자가 전달한 값을 User 클래스를 생성해 저장한다.
    - 회원가입할 때 전달한 값을 저장할 수 있는 필드를 생성한 후 setter와 getter 메소드를 생성한다.
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
- [X] user/profile.html 에서는 Controller에서 전달한 User 데이터를 활용해 사용자 정보를 출력한다.
</div>
</details>

<details>
<summary> URL & API </summary>
<div markdown="1">

| URL | 기능 | 설명 | Response Page | Page Type |
| --- | --- | --- | --- | --- |
| GET / | List All posts | 게시판 index.html  | index.html | 정적 |
| GET /users/form | Get create form | 회원가입 입력 포맷 (form.html) | form.html | 정적 |
| POST /users | create User | 회원가입 | redirect: /users | 동적 |
| GET /users | List All Users | 회원 목록 조회 | /user/list.html | 동적 |
| GET /user/{userId} | Get a User Profile | 회원 profile 조회 | /users/profile.html | 동적 |

</div>
</details>


<details>
<summary> View </summary>
<div markdown="1">

![form](https://i.imgur.com/FQrmnMd.jpg)

![/](https://i.imgur.com/sOj3dTl.jpg)

![/users/{userId}](https://i.imgur.com/1GUeM50.jpg)

</div>
</details>


<details>
<summary> 테스트 </summary>
<div markdown="1">

![UnitTest](https://i.imgur.com/zmIAW72.jpg)

</div>
</details>


</div>
</details>

# Step 2 : 글 쓰기 기능 구현
<details>
<summary> 🖼📝 Step 2 결과와 설명 </summary>
<div markdown="1">

<details>
<summary> URL & API </summary>
<div markdown="1">

| URL               | 기능 | 설명 | Response Page | Page Type | 구현 여부 |
|-------------------| --- | --- | --- | --- | --- |
| step2             |  |  |  |  |  |
| GET /questions    | get create qna | 질문하기 화면 | /qna/form.html | 정적 | ✅ |
| POST /questions   | create qna | 질문하기 생성 | redirect: / | 동적 | ✅ |
| GET /             | List all posts | 글목록 조회 | index.html | 동적 | ✅ |
| GET /articles/{index} | get a article | 특정 글 조회 | /qna/show.html | 동적 | ✅ |
|                   |  |  |  |  |  |
| step2 추가 요구사항(선택) |  |  |  |  |  |
| Get /users/{id}/form | get 개인 정보 수정 화면 | 개인 정보 수정 화면 | /user/updateForm.html | 정적 | ☑️ |
| PUT /users/{id}/update | update User 정보 | 개인정보 수정 | redirect: /users | 동적 | ☑️ |
|                   |  |  |  |  |  |

</div>
</details>


<details>
<summary> View  </summary>
<div markdown="1">

![get /questions](https://i.imgur.com/dKYTAFU.jpg)

![/](https://i.imgur.com/A2Qa3TX.jpg)

![get /articles/1](https://i.imgur.com/LN6MZuC.jpg)

</div>
</details>
<details>
<summary> 테스트 </summary>
<div markdown="1">

`ArticleControllerTest` : @SpringBootTest, @MockMvc를 활용 인수 테스트  
`ArticleServiceStubTest` : Mockito를 활용한 Stub 단위 테스트  
`ArticleServiceTest` : 직접 의존성 주입을 통한 단위 테스트   
`MemoryUserRepositoryTest` : 직접 의존성 주입을 통한 단순 단위 테스트  

![Imgur](https://i.imgur.com/AbvFkwH.jpg)

</div>
</details>

</div>
</details>



---

[![Hits](https://hits.seeyoufarm.com/api/count/incr/badge.svg?url=https%3A%2F%2Fgithub.com%2Fku-kim%2Fjava-spring-cafe&count_bg=%2379C83D&title_bg=%23555555&icon=&icon_color=%23E7E7E7&title=hits&edge_flat=false)](https://hits.seeyoufarm.com)