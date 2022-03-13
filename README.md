# 2022 Java Spring Cafe

2022년도 마스터즈 멤버스 백엔드 스프링 카페 프로젝트

## 스프링 카페 3단계 - DB에 저장하기

---

### 1. 2단계 피드백 반영 + 추가 변경 사항

- [X] String writeTime -> LocalDateTime 타입으로 변경
- [X] isYourPassword & checkIfTheIDIsTheSame 테스트 추가
- [ ] ControllerAdvice 외에 다른 예외처리 방법 고려
- [X] Repository interface name 변경
- [X] UserRepository의 Update를 save로 옮김
- [X] 테스트 name 변경
- [X] UserRepository 내의 private method로 중복 id 검증을 Service Layer로 옮기기
- [X] Service 내에서 DTO 생성 중, View에 쓰일 numbering을 제거하고 템플릿 엔진에서 처리해보기


### 2. 전체 요구사항 체크리스트

- [X] H2 데이터베이스 연동
- [X] 게시글 데이터 저장
- [X] 게시글 목록 구현
- [ ] 게시글 상세보기 구현
- [ ] 사용자 정보 DB에 저장
- [ ] ``Heroku``로 배포

<br>

#### 2.1 H2 데이터베이스 연동 

- yml을 실행용과 테스트용으로 구분
- JDBC Connection 관련 테스트 완료
  - [X] JdbcManualTest : 수동 Connection 테스트
  - [X] JdbcAutoTest : Hikari pool을 이용한 연결 및 DB 조회 테스트
  - [X] SpringJdbcTest : JdbcTemplate를 이용한 DB 조회 테스트

<br>

#### 2.2 게시글 데이터 저장

- Article 클래스를 DB 테이블에 저장할 수 있도록 구현 
  - [ ] Insert 구현 & TEST 

- Article 테이블이 적절한 PK를 가지도록 구현
  - [ ] PK로 기존에 작성된 ``Long id`` 사용 

<br>

#### 2.3 게시글 목록 구현
- 전체 게시글 목록 데이터를 DB에서 조회하도록 구현

<br>

#### 2.4 게시글 상세보기 구현
- 게시글의 세부 내용을 DB에서 가져오도록 구현

<br>

#### 2.5 사용자 정보 DB에 저장
- 회원가입을 통해 등록한 사용자 정보를 DB에 저장한다.

<br>

#### 2.6 배포
- Heroku로 배포를 진행한다.
