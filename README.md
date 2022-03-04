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


