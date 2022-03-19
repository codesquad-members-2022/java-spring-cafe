# 2022 Java Spring Cafe

2022년도 마스터즈 멤버스 백엔드 스프링 카페 프로젝트

<br>

### 1단계

- [x] 회원 가입 기능 구현
- [x] 회원 목록 조회 기능 구현
- [x] 회원 프로필 조회 기능 구현

**한 일**

- html 파일들 모두 static에서 templates로 이동
- html 공통부분 mustache로 추출
- 미션 구현(회원 목록 조회/회원 등록/회원 프로필 조회)
- 유저 정보 저장은 List보다 Map이 적합하다고 판단되어서 Map으로 저장
- MemoryUserRepository, UserService Test code 작성
- 조원분들의 그룹리뷰 조언으로 UserDto 추가

<br>

### 2단계

- [x] 글 쓰기 기능 구현
- [x] 글 목록 조회 구현
- [x] 게시글 상세보기 구현
- [ ] 추가미션 (회원정보 수정)

**한 일**

- 디온의 코드리뷰 반영, 리팩토링
  - 쓸모없는 `@Configuration` 클래스 제거
  - 디미터 법칙 준수
  - 테스트코드 객체 자체 비교하던 것을 toString을 통한 String값으로 내부 값 비교
  - 팩토리 패턴 제거 후 생성자 public으로 변경
- 미션 구현 (글 쓰기, 글 목록 조회, 글 상세보기)

<br>

### 3단계

- [x] H2 DB 연동
- [ ] Heroku 배포

**한 일**

- 로치의 코드리뷰 반영
  - 날짜 형식 상수로 추출
  - test 코드 when 절과 then 절 수정
  - 꼭 필요한 getter, setter만 남겨놓음
- H2 DB로 repository 변경

<br>

### 4단계

- [x] 로그인, 로그아웃 구현
- [ ] 추가구현사항 (개인정보 수정 기능)

**한 일**

- 버그(UserForm, Article에서 LocalDateTime 오류) 수정
- 로그인, 로그아웃 기능 구현
- html 페이지 로그인시, 비로그인 시 화면 출력 변경
- UserDto package 변경
