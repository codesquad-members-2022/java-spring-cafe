# 2022 Java Spring Cafe

2022년도 마스터즈 멤버스 백엔드 스프링 카페 프로젝트

**from [코드스쿼드](https://lucas.codesquad.kr/masters-2022)**



## URL convention

| URL                                         | 기능        |
|---------------------------------------------|-----------|
| GET /login                                  | 로그인 화면    |
| POST /do_login                              | 로그인       |
| GET /logout                                 | 로그아웃      |
| GET /users                                  | 회원 목록 조회  |
| POST /users                                 | 회원 가입     |
| GET /users/:id                              | 회원 프로필 조회 |
| GET /users/:id/form                         | 회원 정보 수정  |
| GET /                                       | 글 목록 조회하기 |
| GET /questions/                             | 질문하기      |
| POST /questions/                            | 글쓰기       |
| GET /questions/:id                          | 게시글 상세보기  |
| GET /questions/:id/form                     | 게시글 수정 화면 |
| PUT /questions/:id                      | 게시글 수정    |
| DELETE /questions/:id                      | 게시글 삭제    |
| POST /questions/:questionId/reply           | 댓글추가      |
| DELETE /questions/:questionId/reply/:replyId | 댓글삭제      |


#### 프로그래밍 요구사항
- 스프링 부트, 웹 MVC
  - 버전 2.6.2
- template 기반 : mustache
- H2 DB
  - jdbc

<br>

---


<br>

## 회원가입 및 개인정보

<details>
<summary>🎃 개인정보 수정</summary>

## 개인정보 수정
- 개인정보는 로그인한 사용자, 해당 정보의 개정과 일치하는 사용자만이 수정할 수 있다.
- 비밀번호가 일치해야만 수정할 수 있다.
  - 비밀번호 입력 3회 제한
    - 제한에 해당되면 10분의 시간 동안 비밀번호 입력 재시도 할 수 없다.
  - 로그아웃 후에도 로그인시 비밀번호 입력 제한을 둔다.
  - 버튼과 입력칸을 비활성화 시킨다.
  - 제한 시간임을 안내 메시지로 알려준다.
- 수정은 사용자 이름, 이메일만 수정할 수 있다.
  - 사용자 id는 수정할 수 없다.

</details>


<br>

## 게시글
- 웹사이트의 첫화면은 게시글 목록이다.
  - 게시글 목록은 모든 사람이 볼 수 있다.
  - 게시글 세부 내용은 로그인한 사용자만이 볼 수 있다.


<details>
<summary>👻 게시글 작성하기</summary>

### 게시글 작성하기

#### 설계

- 로그인한 사용자만 게시글을 작성 할 수 있다.
  - '글쓴이' 입력은 사용하지 않는다.
  - Article 글쓴이는 User의 name 이다.
    - 세션 정보를 통해 name을 hidden or readonly 로 담는다.
      - Article의 writer 로 저장된다.
    - 이후 수정하기/삭제하기시 로그인 사용자와 글쓴이의 사용자 아이디가 같아야 한다.
      - name 은 User 테이블에서 unique 하지 않다.
      - Article 테이블을 통해 UserId 를 확인할 수 있어야 한다. ➝ 1️⃣
- 로그인하지 않은 사용자의 '질문하기' 버튼은 클릭시 로그인 페이지로 이동한다.



1️⃣ 사용자는 여러개의 게시글을 작성 할 수 있다. (1:N 관계)
- User의 PK id 를 Article의 FK로 지정한다.


</details>


<br>


<details>
<summary>👻 게시글 수정하기</summary>

- 게시글 수정은 로그인 사용자와 게시글 작성자의 사용자와 동일해야 한다.
  - GET /questions/:id/form : SessionUser의 userId의 id == Article 의 id
- 추후 댓글 등 다른 사용자도 접근 가능한 페이지이기 때문에, 다른 사용자가 수정 버튼 클릭시에는 ~~안내 메시지를~~, 에러 메시지 반환하도록 한다.
  - "작성자가 아닌 사람은 수정하실 수 없습니다." 
  - status code : 400 
- PUT 메서드로 요청
  - 멱등성
  - 리소스 생성 또는 수정

</details>


<br>


<details>
<summary>👻 댓글</summary>

- 로그인 사용자만이 게시글 접근 가능
- 로그인 사용자만이 댓글 달기 가능
- 답변은 게시글에 종속
  - REPLY 접근 URL : questions/{questionId}/reply

</details>
