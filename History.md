
# History
프로젝트 변경 내역을 기록하는 마크다운 문서

---

## Step1

- [ ] 회원 가입 기능 구현
- [ ] 회원 목록 기능 구현
- [ ] 회원 프로필 기능 구현

---

### 회원 도메인

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

---