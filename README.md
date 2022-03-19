# 2022 Java Spring Cafe

2022년도 마스터즈 멤버스 백엔드 스프링 카페 프로젝트

## Step07 ToDO-List
- [x] footer 분리하기
- [x] Ajax 로 댓글 추가하기
- [x] Ajax 로 댓글 삭제하기
- [ ] DB MySQl 로 바꾸기
- [ ] EC2 배포하기

## Step06 ToDO-List

- [x] Reply Class 만들기
- [x] Reply JDBC 로 연결
- [x] 답변 달기 기능 추가 (로그인 한 사용자만)
- [x] 답변 목록 보여주기
- [x] 자신이 쓴 댓글 삭제 가능 (히든필드 delete 매핑 이용)
  추가 요구사항은 미 반영

## Step05 리뷰 내용 수정 사항

- [x] Save, Delete 시 Long 값을 반환하게 했던 이유 고민해보기
- [x] SESSIONED_USER 는 상수로 관리하기
- [x] sessioned_user 변수명 camelCase 로 변경 (변경을 좀 하자면요ㅠ,ㅠ 인텔리제이 자동완성...)
- [ ] PUT 방식으로 변경해 보기

## Step05 ToDO-List

- [x] 로그인을 하도록 interceptor 구현, 등록하기
- [x] 게시글 작성에서 글쓴이 입력 필드 삭제하기
- [x] 수정은 자신의 글만 가능하도록
- [x] 자신의 글만 삭제 가능하도록
- [x] 요구사항과 달리 히든필드 사용X, Control URI 사용할 것
- [x] 예외를 처리하는 ControllerAdvice 추가

## Step04 리뷰 내용 수정 사항
- [x] DbTemplate null 검증부분 고치기
- [x] login메서드 하나만 public으로 만들기 -> Dto받는 메서드만 public으로
- [x] UserController에 에러코드 400으로 수정
- [x] @DisplayName 에 예상 결과도 함축시켜 보기
- [x] UserControllerTest given절 다시 생각해보기

## Step04 ToDo-List
- [x] LoginServce 만들기
  - [x] 아이디로 찾아온 User의 password가 사용자가 넘긴 password와 동일하다면 user 반환
  - [x] 동일하지 않다면 null 반환하기
  - [ ] ~~해당 id의 유저가 있는지 확인: bool값 반환하기~~(생각해보니 아이디로 찾았을때 없으면 그걸로 끝이다... 회원 가입도 아닌데, 중복 아이디 거증을 할 필요는 없는것 같다)
  - [x] 로그아웃 구현하기

- [x] LoginController 만들기
  - [x] 로그인 Form 만들기 (step03에서 수동 구현 했으니, BeanValidation 이번에는 사용하기)
  - [x] "/login"으로 접속 HttpSession에 사용자 정보 추가
  - [x] 로그인 시 사용자 데이터 view로 넘겨서 로그인 사용자 전용 화면 보여주기
  - [x] "/logout"으로 세션 expire 시키기
  - [x] 로그아웃 시 기본 사용자 화면 보여주기

- [x] 사용자 정보 수정 만들기
  - [x] 비밀번호가 같은 경우에만 수정
  - [x] 로그인 한 사용자는 자신의 정보만 수정 가능

## Step03 리뷰 내용 수정 사항
- [x] JDBC template 로 개선해보기
- [ ] DBrepository 예외 던지기
- [x] findByUserId 로 메서드이름 변경
- [x] 자원 청소 부분 중복 제거하기
- [x] @JdbcTest 적용하기 (ArticleRepository, UserRepository)

## Step03 ToDo-List
- [x] user form 검증 로직 작성하기 (BeanValidation 사용하지 말것!)
- [x] article form 검증 로직 작성하기 (BeanValidation 사용하지 말것!)
- [x] 검증 로직을 Controller가 아닌, 따로 분리하기
- [x] @Validated 에노테이션 적용하기
- [x] User DB 연결
- [x] Article DB 연결
- [x] Dto를 전달받아 전달하는 메서드 Service에 구현하기
- [x] ArticleService Unit Test 만들기
- [x] UserService Unit Test 만들기
- [x] UserController Unit Test 만들기
